package com.wow.webapp.dao;


import java.util.List;

import com.wow.webapp.entitymodel.LabSubType;
import com.wow.webapp.entitymodel.LabType;

public interface LabDAO {

	public void save(LabType labType);
	public void save(LabSubType labSubType);
	
	public LabType findLabType(Integer id);
	
	public List<LabType> getLabType();
	public List<LabSubType> getLabSubType(Integer id);
}
