package mysqlpoint.realmysqlpoint.util;

import mysqlpoint.realmysqlpoint.enumerated.ProviderType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;



@Component
public class PathProviderConverter implements Converter<String, ProviderType> {
    @Override
    public ProviderType convert(String provider) {
        return ProviderType.ofProvider(provider);
    }
}
