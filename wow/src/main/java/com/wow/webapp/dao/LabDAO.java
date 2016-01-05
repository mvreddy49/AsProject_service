package com.wow.webapp.dao;


import java.util.List;

import com.wow.webapp.entitymodel.LabType;

public interface LabDAO {

	public void save(LabType labType);
	public List<LabType> getLabType();
}
