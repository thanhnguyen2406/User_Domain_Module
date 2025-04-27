package UserDomain.factory;

import UserDomain.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserFactoryProvider {
    private final Map<UserType, UserFactory> factoryMap;

    @Autowired
    public UserFactoryProvider(List<UserFactory> factories) {
        factoryMap = new HashMap<>();
        for (UserFactory factory : factories) {
            if (factory instanceof DoctorFactory) {
                factoryMap.put(UserType.DOCTOR, factory);
            } else if (factory instanceof PatientFactory) {
                factoryMap.put(UserType.PATIENT, factory);
            }
        }
    }

    public UserFactory getFactory(UserType userType) {
        return factoryMap.get(userType);
    }
}
