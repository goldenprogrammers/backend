package com.actionsMicroservice.dtos;

import com.actionsMicroservice.domain.action.ActionStatus;

import java.util.Arrays;
import java.util.Objects;

public record ActionDTO(String title, String description, String formLink, byte[] image, ActionStatus status) {
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;

        ActionDTO actionDTO = (ActionDTO) object;
        return Objects.equals(title, actionDTO.title) &&
                Objects.equals(description, actionDTO.description) &&
                Objects.equals(formLink, actionDTO.formLink) &&
                Arrays.equals(image, actionDTO.image) &&
                status == actionDTO.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, formLink, Arrays.hashCode(image), status);
    }

    @Override
    public String toString() {
        return "ActionDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", formLink='" + formLink + '\'' +
                ", image=" + Arrays.toString(image) +
                ", status=" + status +
                '}';
    }
}
