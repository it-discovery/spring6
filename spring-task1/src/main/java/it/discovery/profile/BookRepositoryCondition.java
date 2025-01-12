package it.discovery.profile;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;

public class BookRepositoryCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var attributes = metadata.getAnnotationAttributes(CurrentProfile.class.getName());
        var profile = (Profile) attributes.get("value");
        var currentProfile = context.getEnvironment().getProperty("current.profiles", "dev")
                .toLowerCase();
        var profiles = currentProfile.split(",");
        return Arrays.asList(profiles).contains(profile.name().toLowerCase());
    }
}
