package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Collection;

public class JsonCollectionSizeSerializer extends StdSerializer<Collection<?>> implements ContextualSerializer{

    public JsonCollectionSizeSerializer(){
        super(Collection.class, false);
    }

    @Override
    public void serialize(Collection<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.size());
    }

    @Override
    public JsonSerializer<Collection<?>> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        return new JsonCollectionSizeSerializer();
    }
}
