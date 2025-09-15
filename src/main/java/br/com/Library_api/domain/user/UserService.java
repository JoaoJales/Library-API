package br.com.Library_api.domain.user;

import br.com.Library_api.domain.fine.FineRepository;
import br.com.Library_api.domain.loan.LoanRepository;
import br.com.Library_api.domain.loan.LoanStatus;
import br.com.Library_api.dto.fine.GetFineDTO;
import br.com.Library_api.dto.loan.GetLoanSummaryDTO;
import br.com.Library_api.dto.user.GetDetailingUserDTO;
import br.com.Library_api.dto.user.GetUsersDTO;
import br.com.Library_api.dto.user.PutUserDTO;
import br.com.Library_api.dto.user.UserRegisterDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final FineRepository fineRepository;

    public UserService(UserRepository userRepository, LoanRepository loanRepository, FineRepository fineRepository){
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.fineRepository = fineRepository;
    }

    @Transactional
    public User createUser(UserRegisterDTO data){
        if (userRepository.existsByEmail(data.email())){
            throw new IllegalArgumentException("email already registered");
        }

        var user = new User(data);
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User updateUser(@Valid PutUserDTO data) {
        if (userRepository.existsByEmail(data.email())){
            throw new IllegalArgumentException("email already registered");
        }

        var user = findUser(data.id());
        user.updateInfoUser(data);
        userRepository.save(user);

        return user;
    }

    public Page<GetUsersDTO> getUsers(Pageable pageable){
        return userRepository.findAllByActiveTrue(pageable).map(GetUsersDTO::new);
    }

    public GetDetailingUserDTO getDetailingUser(Long id){
        var user = findUser(id);

        return new GetDetailingUserDTO(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        var user = findUser(id);
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

    public Page<GetLoanSummaryDTO> getUserLoanHistory(Pageable pageable ,Long id) {
        return loanRepository.findLoanHistoryByUser(pageable, id).map(GetLoanSummaryDTO::new);
    }

    public Page<GetLoanSummaryDTO> getUserActiveLoans(Pageable pageable, Long id) {
        return loanRepository.findLoansByUserAndLoanStatus(pageable, id, LoanStatus.ACTIVE).map(GetLoanSummaryDTO::new);
    }

    public Page<GetLoanSummaryDTO> getUserLateLoans(Pageable pageable, Long id) {
        return loanRepository.findLoansByUserAndLoanStatus(pageable, id, LoanStatus.LATE).map(GetLoanSummaryDTO::new);
    }

    public Page<GetFineDTO> getFinesPaid(Pageable pageable ,Long id) {
        return fineRepository.findFinesByUserIdAndPaidStatus(pageable, id, true).map(GetFineDTO::new);
    }

    public Page<GetFineDTO> getFinesUnpaid(Pageable pageable ,Long id) {
        return fineRepository.findFinesByUserIdAndPaidStatus(pageable, id, false).map(GetFineDTO::new);
    }

}
