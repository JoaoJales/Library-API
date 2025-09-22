package br.com.Library_api.domain.user;

import br.com.Library_api.domain.fine.FineRepository;
import br.com.Library_api.domain.loan.LoanRepository;
import br.com.Library_api.domain.loan.LoanStatus;
import br.com.Library_api.domain.reservation.ReservationRepository;
import br.com.Library_api.dto.fine.GetFineDTO;
import br.com.Library_api.dto.loan.GetLoanSummaryDTO;
import br.com.Library_api.dto.reservation.GetReservationSummaryDTO;
import br.com.Library_api.dto.user.*;
import br.com.Library_api.infra.security.SecurityService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final FineRepository fineRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;
    private final ReservationRepository reservationRepository;

    public UserService(UserRepository userRepository, LoanRepository loanRepository,
                       FineRepository fineRepository, PasswordEncoder passwordEncoder,
                       SecurityService securityService, ReservationRepository reservationRepository){
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.fineRepository = fineRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public User createUser(UserRegisterDTO data){
        if (userRepository.existsByEmail(data.email())){
            throw new IllegalArgumentException("email already registered");
        }

        String password = passwordEncoder.encode(data.password());

        var user = new User(data, password);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User updateUser(@Valid PutUserDTO data) {
        if (userRepository.existsByEmail(data.email())){
            throw new IllegalArgumentException("email already registered");
        }

        var user = findUser(data.userId());
        user.updateInfoUser(data);
        userRepository.save(user);

        return user;
    }

    public Page<GetUsersDTO> getUsers(Pageable pageable){
        return userRepository.findAllByActiveTrue(pageable).map(GetUsersDTO::new);
    }

    public GetDetailingUserDTO getDetailingUser(){
        var loggedUserId = getIdUserLogged();
        var user = findUser(loggedUserId);

        return new GetDetailingUserDTO(user);
    }

    @Transactional
    public void deleteUser() {
        var loggedUserId = getIdUserLogged();
        var user = findUser(loggedUserId);

        long activeLoans = loanRepository.countActiveLoansByUser(user.getId());

        if (activeLoans > 0){
            throw new IllegalStateException("The user cannot be deactivated while having active loans.");
        }

        user.deleteUser();
        userRepository.save(user);
    }

    private User findUser(Long id){
        var user = userRepository.findByIdAndActiveTrue(id);

        if (user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or is not active");
        }

        return user.get();
    }

    private Long getIdUserLogged() {
        return securityService.getLoggedUserId();
    }

    public Page<GetLoanSummaryDTO> getUserLoanHistory(Pageable pageable) {
        var loggedUserId = getIdUserLogged();
        return loanRepository.findLoanHistoryByUser(pageable, loggedUserId).map(GetLoanSummaryDTO::new);
    }

    public Page<GetLoanSummaryDTO> getUserActiveLoans(Pageable pageable) {
        var loggedUserId = getIdUserLogged();
        return loanRepository.findLoansByUserAndLoanStatus(pageable, loggedUserId, LoanStatus.ACTIVE).map(GetLoanSummaryDTO::new);
    }

    public Page<GetLoanSummaryDTO> getUserLateLoans(Pageable pageable) {
        var loggedUserId = getIdUserLogged();
        return loanRepository.findLoansByUserAndLoanStatus(pageable, loggedUserId, LoanStatus.LATE).map(GetLoanSummaryDTO::new);
    }

    public Page<GetFineDTO> getFinesPaid(Pageable pageable) {
        var loggedUserId = getIdUserLogged();
        return fineRepository.findFinesByUserIdAndPaidStatus(pageable, loggedUserId, true).map(GetFineDTO::new);
    }

    public Page<GetFineDTO> getFinesUnpaid(Pageable pageable) {
        var loggedUserId = getIdUserLogged();
        return fineRepository.findFinesByUserIdAndPaidStatus(pageable, loggedUserId, false).map(GetFineDTO::new);
    }


    public User alterPassword(@Valid PutPasswordDTO data) {
        var userLogged = securityService.getPrincipalUserLogged();

        if (data.oldPassword().equals(data.newPassword())){
            throw new IllegalArgumentException("The new password must be different from the current password");
        }

        var isOldPasswordCorrect = passwordEncoder.matches(data.oldPassword(), userLogged.getPassword());

        if (!isOldPasswordCorrect){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password incorrect");
        }

        userLogged.updatePassword(passwordEncoder.encode(data.newPassword()));
        userRepository.save(userLogged);

        return userLogged;
    }

    public Page<GetReservationSummaryDTO> getReservationsByUser(Pageable pageable) {
        var loggedUserId = getIdUserLogged();
        return reservationRepository.findReservationHistoryByUser(pageable, loggedUserId).map(GetReservationSummaryDTO::new);
    }

    public Page<GetReservationSummaryDTO> getReservationsActivesOrFulfilled(Pageable pageable) {
        var loggedUserId = getIdUserLogged();
        return reservationRepository.findReservationsActivesOrFulfilledByUser(pageable, loggedUserId).map(GetReservationSummaryDTO::new);
    }
}
