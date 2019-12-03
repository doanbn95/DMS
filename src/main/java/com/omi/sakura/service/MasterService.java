package com.omi.sakura.service;

import com.omi.sakura.persistent.domain.Master;
import com.omi.sakura.request.MasterRequest;

import java.util.List;

public interface MasterService {

    List<Master> findMasterByType(int type);

    boolean create(MasterRequest request);
}
