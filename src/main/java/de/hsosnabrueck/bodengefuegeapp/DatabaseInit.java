package de.hsosnabrueck.bodengefuegeapp;

import de.hsosnabrueck.bodengefuegeapp.model.*;
import de.hsosnabrueck.bodengefuegeapp.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseInit {

    private final CropRepository cropRepository;
    private final SoilRepository soilRepository;
    private final UserRepository userRepository;
    private final FieldRepository fieldRepository;
    private final SampleRepository sampleRepository;

    private boolean isCropEmpty = true;
    private boolean isSoilEmpty = true;
    private boolean isUserEmpty = true;

    public DatabaseInit(CropRepository cropRepository, SoilRepository soilRepository, UserRepository userRepository, FieldRepository fieldRepository, SampleRepository sampleRepository) {
        this.cropRepository = cropRepository;
        this.soilRepository = soilRepository;
        this.userRepository = userRepository;
        this.fieldRepository = fieldRepository;
        this.sampleRepository = sampleRepository;
    }

    @Bean(name = "createCrops")
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

    @Bean(name = "createSoils")
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

    @Bean
    @DependsOn("createSoils")
    public void createDefaultInput() {
        userRepository.findAll().forEach(user -> isUserEmpty = false);

        if (isUserEmpty) {
            User user = new User("user", "user@test", "secret");
            user.setJwtToken("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJkZS5oc29zbmFicnVlY2suYm9kZW5nZWZ1ZWdlIiwiaWQiOjI1NSwidXNlciI6InVzZXIxMkBlbWFpbC5kZSJ9.plfKPhE8Cj5RDaHQo66eGfFHvlWWJgSLgElBL9xYonFsFXwQBN4ftWarF-KeGHCPR-CI0OIY4ghVZ96ZdKU0Xg");

            List<Crop> favCrops = new ArrayList<>();
            favCrops.add(cropRepository.findByName("Kartoffel"));
            favCrops.add(cropRepository.findByName("Mais"));
            favCrops.add(cropRepository.findByName("Zwiebel"));
            user.setFavCrops(favCrops);

            userRepository.save(user);

            Soil soil = new Soil();
            for (Soil s : soilRepository.findAll()) {
                soil = s;
            }

            Field field = new Field("TestField", user, new Grade(24.5, 42.42), soil);
            fieldRepository.save(field);

            Crop crop = new Crop();
            for (Crop c : cropRepository.findAll()) {
                crop = c;
            }

            Sample sample = new Sample(field, LocalDateTime.now().toString(), null, crop, new Grade(24.5, 42.42));

            List<Property> properties = new ArrayList<>();
            properties.add(new Property(1, null, sample, "Struktur der Oberfläche"));
            properties.add(new Property(2, null, sample, "Durchwurzelung des Bodens"));
            properties.add(new Property(3, null, sample, "Makroporen/Bioporen"));
            properties.add(new Property(4, null, sample, "Gefüge und Verfestigungen"));
            properties.add(new Property(5, null, sample, "Organische Reststoffe"));
            properties.add(new Property(4, null, sample, "Farbe und Geruch"));

            sampleRepository.save(sample);
        }
    }
}
