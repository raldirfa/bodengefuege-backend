package de.hsosnabrueck.bodengefuegeapp;

import de.hsosnabrueck.bodengefuegeapp.model.Crop;
import de.hsosnabrueck.bodengefuegeapp.model.Soil;
import de.hsosnabrueck.bodengefuegeapp.repository.CropRepository;
import de.hsosnabrueck.bodengefuegeapp.repository.SoilRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseInit {

    private final CropRepository cropRepository;
    private final SoilRepository soilRepository;

    private boolean isCropEmpty = true;
    private boolean isSoilEmpty = true;

    public DatabaseInit(CropRepository cropRepository, SoilRepository soilRepository) {
        this.cropRepository = cropRepository;
        this.soilRepository = soilRepository;
    }

    @Bean
    public void createCrops() {
        cropRepository.findAll().forEach(crop -> isCropEmpty = false);
        if (isCropEmpty) {
            List<Crop> crops = new ArrayList<>();
            crops.add(new Crop("Kartoffel"));
            crops.add(new Crop("Getreide"));
            crops.add(new Crop("Zuckerrüben"));
            crops.add(new Crop("Grünroggen"));
            crops.add(new Crop("Karotten"));
            crops.add(new Crop("Mais"));
            crops.add(new Crop("Raps"));
            crops.add(new Crop("Roggen"));
            crops.add(new Crop("Salat"));
            crops.add(new Crop("Weißkohl"));
            crops.add(new Crop("Weizen"));
            crops.add(new Crop("Zwiebel"));
            cropRepository.saveAll(crops);
        }
    }

    @Bean
    public void createSoils() {
        soilRepository.findAll().forEach(crop -> isSoilEmpty = false);
        if (isSoilEmpty) {
            List<Soil> soils = new ArrayList<>();
            soils.add(new Soil("Sand"));
            soils.add(new Soil("Moor"));
            soils.add(new Soil("Schluff"));
            soils.add(new Soil("schwach lehmiger Sand"));
            soils.add(new Soil("stark lehmiger Sand"));
            soils.add(new Soil("Anmoor"));
            soils.add(new Soil("Niedermoor"));
            soils.add(new Soil("schluffiger Lehm"));
            soils.add(new Soil("reiner Ton"));
            soilRepository.saveAll(soils);
        }
    }
}
