package fr.isped.sitis.projetInf203;

import jena.tools.JenaEngine;
import org.apache.jena.rdf.model.Model;

public class AppUnivQ3 {

    /**
     * @param args
     * the command line arguments
     */
    public static void main(String[] args) {
        String NS = "";
        // lire le model a partir d'une ontologie
        Model model = JenaEngine.readModel("data/university.owl");
        if (model != null) {
            //lire le Namespace de l’ontologie
            NS = model.getNsPrefixURI("");

            // Ajouter un nouvel étudiant
            // Ajouter un nouvel étudiant dans le modèle: Student3, Fabien Barbier, étudie PM204
            JenaEngine.createInstanceOfClass(model, NS, "Student", "Student4");
            JenaEngine.updateValueOfDataTypeProperty(model, NS, "Student4", "first_name", "Fabien");
            JenaEngine.updateValueOfDataTypeProperty(model, NS, "Student4", "last_name", "Barbier");
            JenaEngine.updateValueOfObjectProperty(model, NS, "Student4", "studies", "M204");

            //model.write(System.out);

            // Appliquer des règles via owlInferencedModel (swrl model)
            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "rule/univ-rules.txt");
            // query on the model after inference
            System.out.println(JenaEngine.executeQueryFile(inferedModel,
                    "query/q3-univ.txt"));
        } else {
            System.out.println("Error when reading model from ontology");
        }
    }

}
