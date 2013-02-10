package org.coenraets.util;

import org.coenraets.model.Wine;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author Aurelien Broszniowski
 */
public class WineBuilder {

  public Wine next() {
    SecureRandom rnd = new SecureRandom();
    String name = names[rnd.nextInt(names.length - 1)];
    String grape = grapes[rnd.nextInt(grapes.length - 1)];
    String country = countries[rnd.nextInt(countries.length - 1)];
    String region = regions[rnd.nextInt(regions.length - 1)];
    String year = "" + (1990 + rnd.nextInt(20));
    String picture = null;
    String desc = UUID.randomUUID().toString();

    return new Wine(name, grape, country, region, year, picture, desc);
  }

  String[] names = new String[] {
      "gewurztraminer", "pinot", "riesling", "sylvaner", "tokay-pinot gris",
      "cremant d'alsace", "gewurztraminer", "pinot", "riesling", "sylvaner",
      "tokay-pinot gris", "cremant d'alsace", "barsac", "bordeaux", "cotes de bourg",
      "cotes de castillon", "cotes de francs", "entre-deux-mers", "fronsac", "graves",
      "haut-medoc", "listrac-medoc", "margaux", "medoc", "moulis en medoc", "pauillac",
      "pessac leognan", "pomerol", "premieres cotes de bordeaux", "saint-emilion", "saint-estephe",
      "saint-julien", "sauternes", "bourgogne", "bourgogne aligote", "chablis", "chassagne-montrachet",
      "clos de vougeot", "corton", "cote de beaune", "cote de nuits-villages", "gevrey-chambertin",
      "givry", "macon", "mercurey", "meursault", "nuit-saint-georges", "pommard", "pouilly-fuisse",
      "rully", "volnay", "vosne-romanee", "chateau-grillet", "chateauneuf-du-pape", "condrieu",
      "cornas", "cote rotie", "cotes du rhone", "cotes du rhone-villages", "cotes du ventoux", "crozes-hermitage",
      "gigondas", "hermitage", "corbieres", "costieres de nimes", "coteaux du languedoc", "cotes du roussillon",
      "fitou", "minervois", "saint-chinian", "anjou", "bourgueil", "chinon", "coteaux du layon", "muscadet",
      "pouilly-fume", "sancerre", "saumur", "touraine", "vouvray", "bandol", "bellet", "cotes de provence",
      "ajaccio", "patrimonio", "bergerac", "buzet", "cahors", "gaillac", "jurancon", "madiran", "monbazillac"
  };

  String[] grapes = new String[] {
      "red", "white", "syrah", "pinot", "grenache", "stones", "chenin"
  };

  String[] countries = new String[] {
      "france", "australia", "california", "spain", "italia", "argentina"
  };

  String[] regions = new String[] {
      "Rochefort sur Loire", "Saint Lambert du Lattay", "Beaulieu sur Layon", "Saint Aubin de Luigné",
      "Faye d'Anjou", "Concourson sur Layon"
  };

}
