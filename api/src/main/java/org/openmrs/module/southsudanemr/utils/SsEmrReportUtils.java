package org.openmrs.module.southsudanemr.utils;

import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameterizable;
import org.openmrs.module.reporting.evaluation.parameter.ParameterizableUtil;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.definition.service.ReportDefinitionService;
import org.openmrs.module.reporting.report.manager.ReportManager;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SsEmrReportUtils {
	
	/**
	 * Purges a Report Definition from the database
	 * 
	 * @param reportManager the Report Definition
	 */
	public static void purgeReportDefinition(ReportManager reportManager) {
		ReportDefinition findDefinition = findReportDefinition(reportManager.getUuid());
		ReportDefinitionService reportService = (ReportDefinitionService) Context.getService(ReportDefinitionService.class);
		if (findDefinition != null) {
			reportService.purgeDefinition(findDefinition);
			
			// Purge Global property used to track version of report definition
			String gpName = "reporting.reportManager." + reportManager.getUuid() + ".version";
			GlobalProperty gp = Context.getAdministrationService().getGlobalPropertyObject(gpName);
			if (gp != null) {
				Context.getAdministrationService().purgeGlobalProperty(gp);
			}
		}
	}
	
	/**
	 * Returns the Report Definition matching the provided uuid.
	 * 
	 * @param uuid Report Uuid
	 * @throws RuntimeException a RuntimeException if the Report Definition can't be found
	 * @return Report Definition object
	 */
	public static ReportDefinition findReportDefinition(String uuid) {
		ReportDefinitionService reportService = Context.getService(ReportDefinitionService.class);
		return reportService.getDefinitionByUuid(uuid);
	}
	
	/**
	 * Setup a Report Definition in a database
	 * 
	 * @param reportManager the Report Definition
	 */
	public static void setupReportDefinition(ReportManager reportManager) {
		ReportManagerUtil.setupReport(reportManager);
	}
	
	/**
	 * @param parameterizable
	 * @param mappings
	 * @param <T>
	 * @return
	 */
	public static <T extends Parameterizable> Mapped<T> map(T parameterizable, String mappings) {
		if (parameterizable == null) {
			throw new IllegalArgumentException("Parameterizable cannot be null");
		}
		if (mappings == null) {
			mappings = ""; // probably not necessary, just to be safe
		}
		return new Mapped<T>(parameterizable, ParameterizableUtil.createParameterMappings(mappings));
	}
	
	public static Date getStartOfDay() {
		return DateUtil.getStartOfDay(new Date());
	}
	
	public static Date getEndOfDay() {
		return DateUtil.getEndOfDay(new Date());
	}
	
	public static String formatDate(Date date) {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormatter.format(date);
	}
}
