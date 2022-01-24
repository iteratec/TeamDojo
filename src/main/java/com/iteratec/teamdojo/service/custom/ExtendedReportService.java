package com.iteratec.teamdojo.service.custom;

import com.iteratec.teamdojo.service.ReportService;
import com.iteratec.teamdojo.service.impl.custom.InstantProviderInjectable;

/**
 * API extension for custom service behaviour
 * <p>
 * See ADR-0001 for more details about this pattern.
 * </p>
 */
public interface ExtendedReportService extends ReportService, InstantProviderInjectable {}
