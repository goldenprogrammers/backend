package com.actionsMicroservice.dtos;

import java.util.List;

public record GetWithPaginationDTO(List<?> data, int totalPages, long totalElements, int pageNumber) {
}
