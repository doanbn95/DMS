package com.omi.sakura.service;

import com.omi.sakura.persistent.domain.Master;
import com.omi.sakura.persistent.repository.MasterRepository;
import com.omi.sakura.request.MasterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterServiceImpl implements MasterService {

    @Autowired
    private MasterRepository repository;

    @Override
    public List<Master> findMasterByType(int type) {
        return repository.findByTypeOrderByIdAsc(type);
    }

    @Override
    public boolean create(MasterRequest request) {
        List<Master> result = repository.findByTypeOrderByIdAsc(request.getType());
        boolean isMaster = false;
        for (Master master : result) {
            if (master.getValue().toLowerCase().equals(request.getValue().toLowerCase())) {
                isMaster = true;
                break;
            }
        }

        if (!isMaster) {
            Master master = Master.builder()
                    .type(request.getType())
                    .value(request.getValue().trim())
                    .build();
            repository.save(master);
            return true;
        } else {
            return false;
        }
    }
}
