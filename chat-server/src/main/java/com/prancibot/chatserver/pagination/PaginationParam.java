package com.prancibot.chatserver.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationParam {
    private int size = 10;
    private int page = 0;
}