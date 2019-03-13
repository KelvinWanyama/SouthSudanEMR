package org.openmrs.module.southsudanemr;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.LocationTag;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsClassLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HealthFacilities {
	
	public static void uploadLocations() throws Exception {
		LocationService locationService = Context.getLocationService();
		InputStream path = OpenmrsClassLoader.getInstance().getResourceAsStream("metadata/facilities.csv");
		
		String line = "";
		String cvsSplitBy = ",";
		String headLine = "";
		String facility_name = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(path, "UTF-8"));
			//exclude the first line as this holds the column headers
			headLine = br.readLine();
			while ((line = br.readLine()) != null) {
				
				String[] records = line.split(cvsSplitBy);
				facility_name = records[1];
				if (StringUtils.isNotEmpty(facility_name)) {
					Location location = locationService.getLocation(facility_name);
					if (location == null) {
						//create the location and associate it with respective metadata
						Location newLocation = new Location();
						newLocation.setName(facility_name);
						newLocation.setCreator(Context.getAuthenticatedUser());
						markAllAsLoginLocations(newLocation);
						locationService.saveLocation(newLocation);
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void retireUnwantedLocations() {
		LocationService locationService = Context.getLocationService();
		List<String> locationUuid = Arrays.asList("2131aff8-2e2a-480a-b7ab-4ac53250262b",
		    "b1a8b05e-3542-4037-bbd3-998ee9c40574", "aff27d58-a15c-49a6-9beb-d30dcfc0c66e",
		    "58c57d25-8d39-41ab-8422-108a0c277d98", "6351fcf4-e311-4a19-90f9-35667d99a8af");
		for (String s : locationUuid) {
			Location location = locationService.getLocationByUuid(s);
			if (location != null && !location.isRetired()) {
				location.setRetired(true);
				location.setRetireReason("Not needed");
				location.setRetiredBy(Context.getAuthenticatedUser());
				location.setDateRetired(new Date());
				locationService.saveLocation(location);
			}
		}
	}
	
	private static void markAllAsLoginLocations(Location location) {
		LocationService service = Context.getLocationService();
		
		Set<LocationTag> allTags = new HashSet<LocationTag>();
		allTags.add(service.getLocationTagByName("Login Location"));
		allTags.add(service.getLocationTagByName("Visit Location"));
		allTags.add(service.getLocationTagByName("Visit Location"));
		allTags.add(service.getLocationTagByName("Transfer Location"));
		allTags.add(service.getLocationTagByName("Admission Location"));
		location.setTags(allTags);
	}
}
