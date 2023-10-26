package com.certificate.microservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public record SignDocumentDTO(
        String id,
        String createdBy,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
        Date created,
        String updatedBy,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ")
        Date updated,
        String name,
        int order,
        String extension,
        String status,
        String type,
        boolean isPendency,
        String processId,
        int versionProcessDocument,
        boolean isBlockDownload
) {}