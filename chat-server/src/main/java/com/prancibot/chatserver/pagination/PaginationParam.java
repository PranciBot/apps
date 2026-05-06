package com.prancibot.chatserver.pagination;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationParam {
    @QueryParam("limit")
    @DefaultValue("10")
    private int limit;

    @QueryParam("offset")
    @DefaultValue("0")
    private int offset;
}