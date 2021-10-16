package com.company;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Enigma {

    private Scanner sc = new Scanner(System.in);

    private HashMap<Integer, String> rotorDictionnary;
    private HashMap<Integer, String> reflecteurDictionnary;

    private String rotor;

    public Enigma() {
        rotorDictionnary = new HashMap<>();
        rotorDictionnary.put(0, "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        rotorDictionnary.put(1, "AJDKSIRUXBLHWTMCQGZNPYFVOE");
        rotorDictionnary.put(2, "BDFHJLCPRTXVZNYEIWGAKMUSQO");
        rotorDictionnary.put(3, "ESOVPZJAYQUIRHXLNFTGKDCMWB");
        rotorDictionnary.put(4, "VZBRGITYUPSDNHLXAWMJQOFECK");

        reflecteurDictionnary = new HashMap<>();
        reflecteurDictionnary.put(0, "YRUHQSLDPXNGOKMIEBFZCWVJAT");
        reflecteurDictionnary.put(1, "RDOBJNTKVEHMLFCWZAXGYIPSUQ");

        this.rotor = rotorDictionnary.get(1);
    }

    public Scanner getSc() {
        return sc;
    }

    // ----------------------------------------------------------------------------------------
    // Fonction qui retourne un entier correspondant à la position d'une lettre donnée dans l'alphabet (A=0, B=1, C=2, ...)
    // Exemples :
    // entrée : 'A' -> sortie 0
    // entrée : 'B' -> sortie 1
    // ...
    // entrée : 'Z' -> sortie 25
    public int lettreEnNombre(char lettre){

        Character.toUpperCase(lettre);
        return (int) lettre - 65;
    }

    // ----------------------------------------------------------------------------------------
    // Fonction qui retourne la lettre associée à une position (un entier) dans l'alphabet (0=A, 1=B, 2=C, ...)
    // Exemples :
    // entrée : 0  -> sortie : 'A'
    // entrée : 1  -> sortie : 'B'
    // ...
    // entrée : 25 -> sortie : 'Z'
    public char nombreEnLettre(int nombre){

        return (char)(65 + nombre);
    }
    // ----------------------------------------------------------------------------------------
    // Fonction qui permet de sélectionner un rotor
    // A partir d'un entier (entre 1 et 5) passé en paramètre, cette fonction retourne le rotor correspondant (la chaîne de caractère correspondante)
    // Exemples :
    // entrée : 1 -> sortie : "EKMFLGDQVZNTOWYHXUSPAIBRCJ"   (ROTOR1)
    // entrée : 4 -> sortie : "ESOVPZJAYQUIRHXLNFTGKDCMWB"   (ROTOR4)
    // ...
    public String choixRotor(int numeroRotor){
//        int tmpNum = numeroRotor -1;
        if(numeroRotor < 0 || numeroRotor > 4) return this.rotor;
        this.rotor = this.rotorDictionnary.get(numeroRotor);
        return this.rotor;
    }

    // ----------------------------------------------------------------------------------------
    // Fonction qui permet à l'utilisateur de sélectionner le réflecteur
    // A partir d'une lettre ('A' ou 'B') passée en paramètre, cette fonction retourne le réflecteur correspondant (la chaîne de caractère)
    // Exemples :
    // entrée : 'A' -> sortie : "YRUHQSLDPXNGOKMIEBFZCWVJAT"    (REFLECTEURA)
    // entrée : 'B' -> sortie : "RDOBJNTKVEHMLFCWZAXGYIPSUQ"    (REFLECTEURB)
    String choixReflecteur(char lettreReflecteur){
        return reflecteurDictionnary.get(lettreEnNombre(lettreReflecteur));
    }

    // ----------------------------------------------------------------------------------------
    // Fonction qui permet à l'utilisateur de la machine de brancher les câbles reliant les paires (6) de lettres
    // Cette fonction doit retourner une chaîne de caractères de 6 lettres majuscules saisies au clavier par l'utilisateur (on supposera que ces 6 lettres sont distinctes)
    // Exemple :
    // Si l'utilisateur saisit les 6 paires suivantes : AV puis DE puis HO puis JK puis LS puis XQ, la fonction doit retourner "AVDEHOJKLSXQ"
    public String cablageInitial(){
        System.out.println("Entrer le cablage initial");
        int count = 0;
        String res = "";
        boolean stringValide = false;
        while (count < 6) {
            String tmp = sc.nextLine();
            while(!stringValide) {
                if(tmp.length() == 2) {
                    stringValide = true;
                    break;
                }
                tmp = sc.nextLine();
            }
            res += tmp.toUpperCase();
            stringValide = false;
            count++;
        }
        return res;
    }
    public void affichageCablageInitial(){
        System.out.println(cablageInitial());
    }
    // ----------------------------------------------------------------------------------------
    // Fonction qui permet de décaler le rotor d'un rang vers la gauche
    // A partir d'une chaîne de caractères passée en paramètre, cette fonction retourne la chaîne de caractères décalée d'un cran vers la gauche, c'est-à-dire que la première lettre est déplacée à la fin de la chaîne.
    // Exemples :
    // entrée : "ABCDEFGHIJKLMNOPQRSTUVWXYZ" -> sortie : "BCDEFGHIJKLMNOPQRSTUVWXYZA"
    // entrée : "IFHUQSMDVHNQOIVHZ" -> sortie : "FHUQSMDVHNQOIVHZI"
    public String decalageUnRang(String rotor){
        String res = "";

        for(char c : rotor.toCharArray()) {
            res = res + ((Character.toUpperCase(c) + 1) > 90 ? 'A' : (char)(Character.toUpperCase(c) + 1));
        }

        return res;
    }
    // ----------------------------------------------------------------------------------------
    // Fonction qui retourne le rotor après avoir défini sa position initiale, c'est-à-dire après nb décalages.
    // A partir d'un rotor donné (une chaîne de caractères) et d'un entier nb donné, cette fonction retourne le rotor décalé de nb crans vers la gauche
    // Exemples :
    // entrées : "ABCDEFGHIJKLMNOPQRSTUVWXYZ" et 3 -> sortie : "DEFGHIJKLMNOPQRSTUVWXYZABC"
    // entrées : "IFHUQSMDVHNQOIVHZ" et 5 -> sortie : "SMDVHNQOIVHZIFHUQ"
    public String positionInitialeRotor(String rotor, int position){
        String res = rotor;
        for(int i = 0 ; i < position ; i++) {
            res = decalageUnRang(res);
        }
        return res;
    }
    // ----------------------------------------------------------------------------------------
    // Fonction qui recherche une lettre dans une chaîne de caractères
    // A partir d'une lettre donnée et d'une chaîne de caractère donnée, cette fonction retourne l'indice (la position) de la lettre dans la chaîne (-1 si absent)
    // Exemples :
    // entrées 'C' et "ABCDE" -> sortie 2
    // entrées 'A' et "ABCDE" -> sortie 0
    // entrées 'E' et "ABCDE" -> sortie 4
    // entrées 'F' et "ABCDE" -> sortie -1
    // entrées 'F' et ROTOR1 -> sortie 3
    int indiceLettre(char lettre, String cablage){
        // A MODIFIER

        int count = 0;
        for(char c : cablage.toCharArray()) {
            if(c == lettre) return count;
            count++;
        }

        return -1;
    }
    // ----------------------------------------------------------------------------------------
    // Fonction qui permet de vérifier si la lettre à décoder est reliée par un câble à une autre lettre. Si oui, elle est transformée en cette lettre, sinon elle reste identique.
    // A partir d'une lettre donnée et d'un cablâge donné (une chaîne de 12 caractères), cette fonction retourne la lettre transformée si elle fait partie d'une paire de lettres d'un des 6 câbles, la même lettre sinon.
    // Exemples :
    // entrées : 'H' et "AVDEHOJKLSXQ" -> sortie 'O'    (car 3ème paire HO)
    // entrées : 'A' et "ABCDEFGHIJKL" -> sortie 'B'    (car 1ère paire AB)
    // entrées : 'B' et "ABCDEFGHIJKL" -> sortie 'A'    (car 1ère paire AB)
    // entrées : 'K' et "ABCDEFGHIJKL" -> sortie 'L'    (car 6ème paire KL)
    // entrées : 'D' et "ABCDEFGHIJKL" -> sortie 'C'    (car 2ème paire CD)
    // entrées : 'M' et "ABCDEFGHIJKL" -> sortie 'M'    (car M absent de la chaîne du cablâge)
    char valeurApresCablageDeDepart(char lettre, String cablage){
        if(cablage.length()%2 != 0) return lettre;
        //if(!cablage.contains("" + lettre)) return lettre;


        for(int i = 0; i < cablage.length() ; i ++) {
            char first = cablage.charAt(i), second = cablage.charAt(++i);
            if(first == lettre) {
                return second;
            } else if (second == lettre){
                return first;
            }
        }
        return lettre;
    }

    // ----------------------------------------------------------------------------------------
    // Fonction qui retourne la nouvelle valeur après le passage dans un rotor
    // A partir d'une lettre donnée et d'un rotor donné (une chaîne de caractères), cette fonction retourne la lettre correspondante à la lettre passée en paramètre après passage dans le rotor donné.
    // Exemples :
    // entrées : 'A' et ROTOR1 -> sortie : 'E'
    // entrées : 'B' et ROTOR1 -> sortie : 'K'
    // entrées : 'Z' et ROTOR1 -> sortie : 'J'
    // entrées : 'E' et "AJDKSIRUXBLHWTMCQGZNPYFVOE" -> sortie : S
    public char passageDansUnRotor(char lettre, String rotor){
        return rotor.charAt(lettreEnNombre(lettre));
    }


    // TODO : FIX REFLECTEUR
    // ----------------------------------------------------------------------------------------
    // Fonction qui retourne la nouvelle valeur après le passage dans le réflecteur
    // A partir d'une lettre donnée et d'un réflecteur donné (une chaîne de caractères), cette fonction retourne la lettre correspondante à la lettre passée en paramètre après passage dans le réflecteur.
    // Exemples :
    // entrées : 'A' et REFLECTEURA -> sortie : 'Y'
    // entrées : 'B' et REFLECTEURA -> sortie : 'R'
    // entrées : 'Z' et "YRUHQSLDPXNGOKMIEBFZCWVJAT" -> sortie : 'T'
    char passageDansLeReflecteur(char lettre, String reflecteur){
        //return valeurApresCablageDeDepart(lettre, reflecteur);
        return reflecteur.charAt(lettreEnNombre(lettre));
    }

    // ----------------------------------------------------------------------------------------
    // Fonction qui retourne la nouvelle valeur après le passage dans un rotor dans le sens inverse (pour le retour)
    // A partir d'une lettre donnée et d'un rotor donné (une chaîne de caractères), cette fonction retourne la lettre correspondante à la lettre passée en paramètre après passage en sens inverse dans le rotor.
    // Exemples :
    // entrées : 'E' et ROTOR1 -> sortie : 'A'
    // entrées : 'K' et ROTOR1 -> sortie : 'B'
    // entrées : 'J' et ROTOR1 -> sortie : 'Z'
    // entrées : 'S' et "AJDKSIRUXBLHWTMCQGZNPYFVOE" -> sortie : E
    public char inverseRotor(char lettre, String rotor){
        //return valeurApresCablageDeDepart(lettre, rotor);
        return rotor.charAt(indiceLettre(lettre, rotor));
    }
    // ----------------------------------------------------------------------------------------
    // Fonction qui transforme une chaîne de caractères en majuscule
    public String enMajuscule(String message){
        return message.toUpperCase();
    }
    // ----------------------------------------------------------------------------------------


    public HashMap<Integer, String> getReflecteurDictionnary() {
        return reflecteurDictionnary;
    }

    public HashMap<Integer, String> getRotorDictionnary() {
        return rotorDictionnary;
    }
}