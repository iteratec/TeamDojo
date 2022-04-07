package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.TeamGroupService;
import com.iteratec.teamdojo.service.impl.custom.InstantProviderInjectable;

/**
 * API extension for custom service behaviour
 * <p>
 * See ADR-0001 for more details about this pattern.
 * </p>
 */
public interface ExtendedTeamGroupService extends TeamGroupService, InstantProviderInjectable {}
