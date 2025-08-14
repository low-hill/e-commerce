package org.example.ecommerce.config;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UuidV7Generator implements IdentifierGenerator {
    private final TimeBasedEpochGenerator generator =  Generators.timeBasedEpochGenerator();

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return generator.generate();
    }
}
