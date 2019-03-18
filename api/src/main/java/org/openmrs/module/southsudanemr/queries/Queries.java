package org.openmrs.module.southsudanemr.queries;

import org.openmrs.module.southsudanemr.utils.SsEmrReportUtils;

public class Queries {
	
	public static final String getAppointmentToday(int conceptId) {
		return "SELECT pa.patient_id FROM patient pa INNER JOIN encounter e ON pa.patient_id=e.patient_id"
		        + " INNER JOIN obs o ON pa.patient_id=o.person_id WHERE o.voided=0 AND o.concept_id=" + conceptId
		        + " AND e.voided=0 AND o.value_datetime IS NOT NULL  AND o.value_datetime BETWEEN '"
		        + SsEmrReportUtils.formatDate(SsEmrReportUtils.getStartOfDay()) + "' AND '"
		        + SsEmrReportUtils.formatDate(SsEmrReportUtils.getEndOfDay()) + "'";
	}
	
	public static final String getClientsWhoVisitedFacilityToday() {
		return "SELECT pa.patient_id FROM patient pa INNER JOIN encounter e ON pa.patient_id=e.patient_id"
		        + " INNER JOIN visit v ON pa.patient_id=v.patient_id WHERE v.voided=0 "
		        + " AND e.voided=0 AND v.date_started IS NOT NULL  AND v.date_started BETWEEN '"
		        + SsEmrReportUtils.formatDate(SsEmrReportUtils.getStartOfDay()) + "' AND '"
		        + SsEmrReportUtils.formatDate(SsEmrReportUtils.getEndOfDay()) + "'";
	}
	
	public static final String getPatientsWhoMissedAppointment(int conceptId) {
		return "SELECT patient_id FROM "
		        + "(SELECT p.patient_id,MAX(encounter_datetime) encounter_datetime "
		        + "FROM patient p INNER JOIN encounter e ON e.patient_id=p.patient_id "
		        + "WHERE p.voided=0 AND e.voided=0 "
		        + "GROUP BY p.patient_id ) max_mov "
		        + "INNER JOIN obs o ON o.person_id=max_mov.patient_id "
		        + "WHERE max_mov.encounter_datetime=o.obs_datetime AND o.voided=0 AND o.value_datetime IS NOT NULL AND o.concept_id="
		        + conceptId + " AND DATEDIFF('" + SsEmrReportUtils.formatDate(SsEmrReportUtils.getStartOfDay())
		        + "' ,o.value_datetime) > 0";
	}
}
