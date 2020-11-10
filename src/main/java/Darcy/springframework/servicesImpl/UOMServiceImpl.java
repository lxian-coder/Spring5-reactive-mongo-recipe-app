package Darcy.springframework.servicesImpl;

import Darcy.springframework.commands.UnitOfMeasureCommand;
import Darcy.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import Darcy.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import Darcy.springframework.services.UnitOfMeassureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Darcy Xian  3/9/20  11:43 am      spring5-recipe-app
 */
@Service
@AllArgsConstructor
public class UOMServiceImpl implements UnitOfMeassureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {
//        return StreamSupport.stream(unitOfMeasureRepository.findAll()
//                       .spliterator(),false)
//                       .map(unitOfMeasureToUnitOfMeasureCommand::convert)
//                       .collect(Collectors.toSet());

    return unitOfMeasureReactiveRepository
            .findAll()
            .map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
