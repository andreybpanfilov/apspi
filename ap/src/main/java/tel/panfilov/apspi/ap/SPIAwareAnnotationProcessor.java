package tel.panfilov.apspi.ap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;

@SupportedAnnotationTypes({
        "tel.panfilov.apspi.ap.SPIAwareAnnotation"
})
public class SPIAwareAnnotationProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        Optional<SPIAwareSettings> settings = getSettings();
        if (settings.isPresent()) {
            messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Processing, settings: " + settings.get().getClass().getName());
        } else {
            messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Processing without settings");
        }
        return true;
    }

    protected Optional<SPIAwareSettings> getSettings() {
        ClassLoader classLoader = SPIAwareAnnotationProcessor.class.getClassLoader();
        return ServiceLoader.load(SPIAwareSettings.class, classLoader)
                .findFirst();
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

}