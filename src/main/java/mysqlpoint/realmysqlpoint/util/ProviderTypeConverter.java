package mysqlpoint.realmysqlpoint.util;



import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import mysqlpoint.realmysqlpoint.enumerated.ProviderType;

@Converter
public class ProviderTypeConverter implements AttributeConverter<ProviderType, String> {
    @Override
    public String convertToDatabaseColumn(ProviderType attribute) {
        return attribute.getProviderName();
    }

    @Override
    public ProviderType convertToEntityAttribute(String dbData) {
        return ProviderType.ofProvider(dbData);
    }
}
