package de.iu.ipwa02.ghostnetfishing.repo;

import de.iu.ipwa02.ghostnetfishing.model.GhostNet;
import de.iu.ipwa02.ghostnetfishing.model.GhostNetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {
    List<GhostNet> findByStatusIn(List<GhostNetStatus> statuses);
}
