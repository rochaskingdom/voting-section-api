package com.vinicius.voting.section.api.domain.model;

import com.vinicius.voting.section.api.controller.request.SectionRequest;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Builder
@Document
public class Section {

    @Id
    private String id;
    private String uuid;
    private String description;
    private LocalDateTime openingDate;
    private LocalDateTime closingDate;
    private Agenda agenda;

    public static Section newSection(final SectionRequest sectionRequest, final Agenda agenda) {
        final var identifier = UUID.randomUUID().toString();
        final var now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        final var closingDate = getClosingDate(sectionRequest, now);
        final var description = sectionRequest.description();
        return Section.builder()
                .uuid(identifier)
                .description(description)
                .openingDate(now)
                .closingDate(closingDate)
                .agenda(agenda)
                .build();
    }

    private static LocalDateTime getClosingDate(SectionRequest sectionRequest, LocalDateTime now) {
        if (StringUtils.isBlank(sectionRequest.closingDateTime())) {
            return now.plusMinutes(1L);
        }
        final var dateTimerFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(sectionRequest.closingDateTime(), dateTimerFormatter);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uuid", uuid)
                .append("openingDate", openingDate)
                .append("closingDate", closingDate)
                .append("agenda", agenda)
                .toString();
    }
}
