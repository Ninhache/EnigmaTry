package com.company;

public class Main {

    // PROGRAMME PRINCIPAL
    public static void main(String[] args) {

        Enigma enigma = new Enigma();


        // write your code here
        System.out.println(" ------------------------------------\n| Simulation d'une machine Enigma M3 |\n ------------------------------------");

        // Initialisation des éléments
        // ---------------------------
        System.out.println("Quel type de configuration souhaitez-vous ? \n 1 : Configuration par défaut (Rotor 1 : III en position W, Rotor 2 : I en position D, Rotor 3 : V en position E, Réflecteur : B, Cablâge : AV - DE - HO - JK - LS - XQ, Message à décoder par défaut)\n 2 : Configuration personnalisée");
        int choix = enigma.getSc().nextInt();
        while (choix<1 || choix>2){
            System.out.println("Erreur de saisie, choix : 1 ou 2");
            choix = enigma.getSc().nextInt();
        }

        int rotor1,rotor2,rotor3;
        String R1,R2,R3;
        char position1,position2,position3;
        int decalage1,decalage2,decalage3;
        char choixRef;
        String refl;
        String cables;

        String message = "";

        if (choix == 1){// Configuration pré-établie
            // // Rotors choisis
            rotor1 = 2;
            rotor2 = 0;
            rotor3 = 4;
            R1 = enigma.choixRotor(rotor1);
            R2 = enigma.choixRotor(rotor2);
            R3 = enigma.choixRotor(rotor3);

            // // Position initiale des rotors choisis
            position1 = 'W';
            position2 = 'D';
            position3 = 'E';
            decalage1 = enigma.indiceLettre(position1,R1);
            R1 = enigma.positionInitialeRotor(R1,decalage1);
            decalage2 = enigma.indiceLettre(position2,R2);
            R2 = enigma.positionInitialeRotor(R2,decalage2);
            decalage3 = enigma.indiceLettre(position3,R3);
            R3 = enigma.positionInitialeRotor(R3,decalage3);

            // // Réflecteur choisi
            choixRef = 'B';
            refl = enigma.choixReflecteur(choixRef);

            // // Initialisation de la configuration du cablage de la machine par l'utilisateur
            cables = "AVDEHOJKLSXQ";

            // // Message à tester
            message = "AKBAOKETGPVYHGWBSGSVUDTZEBNOXGFOBVYOJVTWFPIKC";
        } else {// Configuration choisie par l'utilisateur
            // // Choix des 3 rotors (distincts) parmi les 5
            // NB : On supposera que les numéros des 3 rotors sont bien compris entre 1 et 5 et qu'ils sont tous différents
            System.out.println("Entrez le numéro du premier rotor choisi (1, 2, 3, 4, 5)");
            rotor1 = enigma.getSc().nextInt();
            System.out.println("Entrez le numéro du deuxième rotor choisi");
            rotor2 = enigma.getSc().nextInt();
            System.out.println("Entrez le numéro du troisième rotor choisi");
            rotor3 = enigma.getSc().nextInt();

            R1 = enigma.choixRotor(rotor1);
            R2 = enigma.choixRotor(rotor2);
            R3 = enigma.choixRotor(rotor3);

            // // Choix de la position initiale des rotors choisis
            System.out.println("Entrez la position initiale du premier rotor choisi (A à Z)");
            position1 = enigma.getSc().nextLine().charAt(0);
            System.out.println("DEV > " + position1);

            System.out.println("Entrez la position initiale du deuxième rotor choisi (A à Z)");
            position2 = enigma.getSc().nextLine().charAt(0);
            System.out.println("DEV > " + position2);

            System.out.println("Entrez la position initiale du troisième rotor choisi (A à Z)");
            position3 = enigma.getSc().nextLine().charAt(0);
            System.out.println("DEV > " + position3);

            decalage1 = enigma.indiceLettre(position1,R1);
            R1 = enigma.positionInitialeRotor(R1,decalage1);
            decalage2 = enigma.indiceLettre(position2,R2);
            R2 = enigma.positionInitialeRotor(R2,decalage2);
            decalage3 = enigma.indiceLettre(position3,R3);
            R3 = enigma.positionInitialeRotor(R3,decalage3);

            // // Choix du réflecteur parmi les 2
            System.out.println("Entrez la lettre du réflecteur choisi (A ou B)");
            choixRef = enigma.getSc().nextLine().charAt(0);
            refl = enigma.choixReflecteur(choixRef);

            // // Initialisation de la configuration du cablage de la machine par l'utilisateur
            cables = enigma.cablageInitial();

            // // Le message à coder
            System.out.println("Entrez le message à coder :");
            message = enigma.getSc().nextLine();

            message = enigma.enMajuscule(message);
        }

        String messageDecode="";
        char lettre;

        // Boucle principale du programme Enigma
        // -------------------------------------
        for (int tour = 0 ; tour < message.length() ; tour = tour+1) {// la boucle s'arrête quand on a codé chaque lettre du message
            // 1. Récupération de la lettre courante dans le message à décoder
            lettre = message.charAt(tour);
            // 2. Passage par le câblage
            lettre = enigma.valeurApresCablageDeDepart(lettre, cables);
            // 3. Passage par les 3 rotors (premier, deuxième, troisième)
            // A COMPLETER => passageDansUnRotor
            lettre = enigma.passageDansUnRotor(lettre, R1);
            lettre = enigma.passageDansUnRotor(lettre, R2);
            lettre = enigma.passageDansUnRotor(lettre, R3);

            // 4. Passage par le réflecteur
            // A COMPLETER => passageDansLeReflecteur
            lettre = enigma.passageDansLeReflecteur(lettre, refl);

            // 5. Passage par les 3 rotors (dans le sens inverse : troisième, deuxième, premier)
            // A COMPLETER => inverseRotor
            lettre = enigma.inverseRotor(lettre, R3);
            lettre = enigma.inverseRotor(lettre, R2);
            lettre = enigma.inverseRotor(lettre, R1);

            // 6. Passage par le cablâge
            // A COMPLETER => valeurApresCablageDeDepart
            lettre = enigma.valeurApresCablageDeDepart(lettre, cables);

            // 7. Ajout de la lettre décodée au message
            // A COMPLETER => concaténation
            messageDecode += lettre ;

            // 8. Préparation de l'itération suivante : décalage du premier rotor (à chaque fois); décalage du deuxième rotor si le premier a fait un tour complet (après 26 itérations) ; décalage du troisième rotor si le deuxième a fait un tour complet (après 26*26 itérations)
            // 8.1 Le rotor 1 tourne d'un rang vers la gauche après chaque lettre (donc à chaque tour)
            enigma.getRotorDictionnary().put(rotor1, enigma.decalageUnRang(enigma.getRotorDictionnary().get(rotor1)));

            // 8.2 Si le rotor 1 a effectué un tour (toutes les 26 itérations) alors le rotor 2 tourne d'un cran vers la gauche
            if(tour%26 == 0) enigma.getRotorDictionnary().put(rotor2, enigma.decalageUnRang(enigma.getRotorDictionnary().get(rotor2)));

            // 8.3 Si le rotor 2 a effectué un tour (toutes les 26*26 itérations) alors le rotor 3 tourne d'un cran vers la gauche

            if(tour%676 == 0) enigma.getRotorDictionnary().put(rotor3, enigma.decalageUnRang(enigma.getRotorDictionnary().get(rotor3)));
        }
        System.out.println("\nLe message décodé est : \n" + messageDecode);

    }


}
