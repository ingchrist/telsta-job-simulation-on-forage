package au.com.telstra.simcardactivator;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SimCardActivationService {
    private final SimCardActivationRepository repository;

    public SimCardActivationService(SimCardActivationRepository repository) {
        this.repository = repository;
    }

    public SimCardActivation save(SimCardActivation activation) {
        return repository.save(activation);
    }

    public Optional<SimCardActivation> findById(Long id) {
        return repository.findById(id);
    }
}