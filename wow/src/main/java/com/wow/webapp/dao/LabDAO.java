package com.wow.webapp.dao;


import java.util.Date;
import java.util.List;

import com.wow.webapp.entitymodel.LabSlots;
import com.wow.webapp.entitymodel.LabSubType;
import com.wow.webapp.entitymodel.LabType;

public interface LabDAO {

	public void save(LabType labType);
	public void save(LabSubType labSubType);
	public void save(LabSlots labSlots);
	
	public LabType findLabType(Integer id);
	
	public List<LabType> getLabType();
	public List<LabSubType> getLabSubType(LabType labType);
	
	public LabSubType gerLabsubType(Integer id);
	public List<String> findSlotsByStartAndEndTimes(Date startTime, Date endTime, LabSubType labsubType);
}
