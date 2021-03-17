package com.intellij.lang.jsgraphql.types.schema.idl;

import com.intellij.lang.jsgraphql.types.Internal;
import com.intellij.lang.jsgraphql.types.Scalars;
import com.intellij.lang.jsgraphql.types.language.*;
import com.intellij.lang.jsgraphql.types.schema.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * A wiring factory that will echo back the objects defined.  That is if you have a field called
 * "name" of type String, it will echo back the value "name".  This is ONLY useful for mocking out a
 * schema that do don't want to actually execute properly.
 */
@Internal
public class EchoingWiringFactory implements WiringFactory {

    public static RuntimeWiring newEchoingWiring() {
        return newEchoingWiring(x -> {
        });
    }

    public static RuntimeWiring newEchoingWiring(Consumer<RuntimeWiring.Builder> builderConsumer) {
        RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
        builderConsumer.accept(builder);
        return builder
                .wiringFactory(new EchoingWiringFactory())
                .build();
    }

    @Override
    public boolean providesTypeResolver(InterfaceWiringEnvironment environment) {
        return true;
    }

    @Override
    public TypeResolver getTypeResolver(InterfaceWiringEnvironment environment) {
        return env -> env.getSchema().getQueryType();
    }

    @Override
    public boolean providesTypeResolver(UnionWiringEnvironment environment) {
        return true;
    }

    @Override
    public TypeResolver getTypeResolver(UnionWiringEnvironment environment) {
        return env -> env.getSchema().getQueryType();
    }

    @Override
    public DataFetcher getDefaultDataFetcher(FieldWiringEnvironment environment) {
        return env -> {
            GraphQLOutputType fieldType = env.getFieldType();
            if (fieldType instanceof GraphQLObjectType) {
                return fakeObjectValue((GraphQLObjectType) fieldType);
            } else {
                PropertyDataFetcher<Object> df = new PropertyDataFetcher<>(env.getFieldDefinition().getName());
                return df.get(env);
            }
        };
    }


    private static Object fakeObjectValue(GraphQLObjectType fieldType) {
        Map<String, Object> map = new LinkedHashMap<>();
        fieldType.getFieldDefinitions().forEach(fldDef -> {
            GraphQLOutputType innerFieldType = fldDef.getType();
            Object obj = null;
            if (innerFieldType instanceof GraphQLObjectType) {
                obj = fakeObjectValue((GraphQLObjectType) innerFieldType);
            } else if (innerFieldType instanceof GraphQLScalarType) {
                obj = fakeScalarValue(fldDef.getName(), (GraphQLScalarType) innerFieldType);
            }
            map.put(fldDef.getName(), obj);

        });
        return map;
    }

    private static Object fakeScalarValue(String fieldName, GraphQLScalarType scalarType) {
        if (scalarType.equals(Scalars.GraphQLString)) {
            return fieldName;
        } else if (scalarType.equals(Scalars.GraphQLBoolean)) {
            return true;
        } else if (scalarType.equals(Scalars.GraphQLInt)) {
            return 1;
        } else if (scalarType.equals(Scalars.GraphQLFloat)) {
            return 1.0;
        } else if (scalarType.equals(Scalars.GraphQLID)) {
            return "id_" + fieldName;
        } else if (scalarType.equals(Scalars.GraphQLBigDecimal)) {
            return new BigDecimal(1.0);
        } else if (scalarType.equals(Scalars.GraphQLBigInteger)) {
            return new BigInteger("1");
        } else if (scalarType.equals(Scalars.GraphQLByte)) {
            return Byte.valueOf("1");
        } else if (scalarType.equals(Scalars.GraphQLShort)) {
            return Short.valueOf("1");
        } else {
            return null;
        }
    }

    public static GraphQLScalarType fakeScalar(String name) {
        return new GraphQLScalarType(name, name, new Coercing() {
            @Override
            public Object serialize(Object dataFetcherResult) {
                return dataFetcherResult;
            }

            @Override
            public Object parseValue(Object input) {
                return input;
            }

            @Override
            public Object parseLiteral(Object input) {
                if (input instanceof ScalarValue) {
                    if (input instanceof IntValue) {
                        return ((IntValue) input).getValue();
                    } else if (input instanceof FloatValue) {
                        return ((FloatValue) input).getValue();
                    } else if (input instanceof StringValue) {
                        return ((StringValue) input).getValue();
                    } else if (input instanceof BooleanValue) {
                        return ((BooleanValue) input).isValue();
                    }
                }
                return input;
            }
        });
    }
}

