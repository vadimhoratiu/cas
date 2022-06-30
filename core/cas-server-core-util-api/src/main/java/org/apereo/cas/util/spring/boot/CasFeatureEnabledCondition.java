package org.apereo.cas.util.spring.boot;

import org.apereo.cas.configuration.features.CasFeatureModule;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * This is {@link CasFeatureEnabledCondition}.
 *
 * @author Misagh Moayyed
 * @since 6.5.0
 */
@Slf4j
public class CasFeatureEnabledCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(final ConditionContext context,
                                            final AnnotatedTypeMetadata metadata) {
        val attributes = metadata.getAnnotationAttributes(ConditionalOnFeatureEnabled.class.getName());
        val name = attributes.get("feature").toString();
        val module = attributes.get("module").toString();
        val enabledByDefault = BooleanUtils.toBoolean(attributes.get("enabledByDefault").toString());

        val feature = CasFeatureModule.FeatureCatalog.valueOf(name);
        val property = feature.toProperty(module);
        LOGGER.trace("Checking for feature module capability via [{}]", property);

        if (!context.getEnvironment().containsProperty(property) && !enabledByDefault) {
            val message = "CAS feature " + property + " is disabled by default and must be explicitly enabled.";
            LOGGER.trace(message);
            return ConditionOutcome.noMatch(message);
        }

        val propertyValue = context.getEnvironment().getProperty(property);
        if (StringUtils.equalsIgnoreCase(propertyValue, "false")) {
            val message = "CAS feature " + property + " is set to false.";
            LOGGER.trace(message);
            return ConditionOutcome.noMatch(message);
        }
        val message = "CAS feature " + property + " is set to true.";
        LOGGER.trace(message);
        feature.register(module);
        return ConditionOutcome.match(message);
    }
}
