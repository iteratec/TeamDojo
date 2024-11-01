/*
 SPDX-FileCopyrightText: the TeamDojo authors
 SPDX-License-Identifier: Apache-2.0
 */
package com.iteratec.teamdojo.web.rest.custom;

import com.iteratec.teamdojo.service.dto.custom.ServerInfoDTO;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for providing information about the server instance.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CustomServerInfoResource {

    /**
     * GET  /server/info : provide information concerning the server instance.
     *
     * @return the login if the user is authenticated
     */
    @GetMapping("/server/info")
    public ServerInfoDTO getServerTime() {
        log.debug("REST request for server time");
        return new ServerInfoDTO(Instant.now());
    }
}
