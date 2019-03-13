package org.openmrs.module.southsudanemr.reports;

import org.openmrs.module.reporting.ReportingException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.southsudanemr.reporting.library.dataset.AppointmentDataset;
import org.openmrs.module.southsudanemr.reporting.manager.SsEmrDataExportManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Component
public class DailyAppointmentsReport extends SsEmrDataExportManager {
	
	@Autowired
	private AppointmentDataset appointmentDateset;
	
	@Override
	public String getExcelDesignUuid() {
		return "7736c74a-3c19-11e9-bb13-6ff8a5449487";
	}
	
	@Override
	public String getUuid() {
		return "eaceb9a2-3c0e-11e9-ba7f-d3c5189e6a0b";
	}
	
	@Override
	public String getName() {
		return "Daily Appointments";
	}
	
	@Override
	public String getDescription() {
		return "Daily Appointments for South Sudan";
	}
	
	@Override
	public ReportDefinition constructReportDefinition() {
		ReportDefinition rd = new ReportDefinition();
		rd.setUuid(getUuid());
		rd.setName(getName());
		rd.setDescription(getDescription());
		rd.setParameters(getParameters());
		
		rd.addDataSetDefinition("A", Mapped.mapStraightThrough(appointmentDateset.constructAppointmentDataset()));
		
		return rd;
	}
	
	@Override
	public String getVersion() {
		return "1.0-SNAPSHOT";
	}
	
	@Override
	public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
		ReportDesign reportDesign = null;
		try {
			reportDesign = createXlsReportDesign(reportDefinition, "daily.xls", "Daily Appointments", getExcelDesignUuid(),
			    null);
			Properties props = new Properties();
			props.put("sortWeight", "5000");
			reportDesign.setProperties(props);
		}
		catch (IOException e) {
			throw new ReportingException(e.toString());
		}
		
		return Arrays.asList(reportDesign);
	}
}
