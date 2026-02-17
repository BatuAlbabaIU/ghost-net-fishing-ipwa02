package de.iu.ipwa02.ghostnetfishing.web;

import de.iu.ipwa02.ghostnetfishing.model.GhostNet;
import de.iu.ipwa02.ghostnetfishing.model.GhostNetStatus;
import de.iu.ipwa02.ghostnetfishing.repo.GhostNetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GhostNetController {

    private final GhostNetRepository repo;

    public GhostNetController(GhostNetRepository repo) {
        this.repo = repo;
    }

    // Startseite: offene Netze anzeigen
    @GetMapping("/")
    public String home(Model model) {
        List<GhostNet> open = repo.findByStatusIn(List.of(
                GhostNetStatus.GEMELDET,
                GhostNetStatus.BERGUNG_BEVORSTEHEND
        ));
        model.addAttribute("nets", open);
        return "index";
    }

    // 1) Netz melden (anonym möglich)
    @GetMapping("/report")
    public String reportForm(Model model) {
        model.addAttribute("ghostNet", new GhostNet());
        return "report";
    }

    @PostMapping("/report")
    public String reportSubmit(@ModelAttribute GhostNet ghostNet) {
        ghostNet.setStatus(GhostNetStatus.GEMELDET);
        repo.save(ghostNet);
        return "redirect:/";
    }

    // Detailseite
    @GetMapping("/net/{id}")
    public String detail(@PathVariable Long id, Model model) {
        GhostNet net = repo.findById(id).orElseThrow();
        model.addAttribute("net", net);
        return "detail";
    }

    // 2) Zur Bergung übernehmen
    @PostMapping("/net/{id}/claim")
    public String claim(@PathVariable Long id,
                        @RequestParam String salvorName,
                        @RequestParam String salvorPhone) {

        GhostNet net = repo.findById(id).orElseThrow();

        // nur wenn noch niemand zugeordnet ist
        if (net.getSalvorName() == null || net.getSalvorName().isBlank()) {
            net.setSalvorName(salvorName);
            net.setSalvorPhone(salvorPhone);
            net.setStatus(GhostNetStatus.BERGUNG_BEVORSTEHEND);
            repo.save(net);
        }

        return "redirect:/net/" + id;
    }

    // 3) Als geborgen markieren
    @PostMapping("/net/{id}/recovered")
    public String recovered(@PathVariable Long id) {
        GhostNet net = repo.findById(id).orElseThrow();
        net.setStatus(GhostNetStatus.GEBORGEN);
        repo.save(net);
        return "redirect:/";
    }

    // 4) Als verschollen melden (Name/Telefon Pflicht)
    @PostMapping("/net/{id}/missing")
    public String missing(@PathVariable Long id,
                          @RequestParam String reporterName,
                          @RequestParam String reporterPhone) {

        GhostNet net = repo.findById(id).orElseThrow();

        if (reporterName == null || reporterName.isBlank()
                || reporterPhone == null || reporterPhone.isBlank()) {
            return "redirect:/net/" + id + "?error=reporterRequired";
        }

        net.setReporterName(reporterName);
        net.setReporterPhone(reporterPhone);
        net.setStatus(GhostNetStatus.VERSCHOLLEN);
        repo.save(net);

        return "redirect:/";
    }
}
