
import java.io.BufferedInputStream;
import java.util.*;
import javax.sound.sampled.*;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class Main {
    public static final String RESET = "\u001B[0m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AZUL = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLANCO = "\u001B[90m";


    private static String NombreJugador;

    private static int[] AtributosSaludClase = {/*0*/125, /*1*/100, /*2*/75};
    private static int[] AtributosDañoClase = {/*0*/10, /*1*/6, /*2*/4};
    private static int[] AtributosAgilidadClase = {/*0*/4, /*1*/3, /*2*/3};
    private static double[] AtributosCriticoClase = {/*0*/0.30, /*1*/0.25, /*2*/0.2};
    private static double[] AtributosReduccionDañoClase = {/*0*/0.90, /*1*/0.70, /*2*/0.6};

    private static String[] Objetos = new String[19];
    private static String[] Equipamiento = new String[5];
    private static int[] Cantidades = new int[19];
    private static int[] Salud = new int[5];
    private static int[] SaludMax = new int[1];
    private static int[] Daño = new int[4];
    private static int[] Agilidad = new int[7];


    private static int Experiencia = 0;
    private static int ExperienciaMax = 35;
    private static int NivelJugador = 1;

    private static int SaludArmaduraActual = 0; //para usar en el encantamiento
    private static int SaludEscudoActual = 0;


    private static int Gemas;

    private static String[] NombreVarita = new String[6];
    private static int AgilidadBaston;
    private static int FuerzaBaston;
    private static boolean NivelBaston_1 = false;
    private static boolean NivelBaston_2 = false;
    private static boolean NivelBaston_3 = false;
    private static int[] EnergiaVarita = new int[6];
    private static int[] MaxEnergiaVarita = new int[6];
    private static int[] DañoVarita = new int[6];
    private static boolean[] VaritaActiva = new boolean[6];
    private static ArrayList<Integer> varitasPoseidas = new ArrayList<>(); // Lista de índices de varitas poseídas

    private static int ReduccionDañoEspada;
    private static double PorcentajeReduccionDañoEspada;




    private static ArrayList<Integer> monstruosInvocados = new ArrayList<>();
    private static String[] NivelMonstruo = {/*0*/"1", /*1*/"1", /*2*/"1",
            /*3*/"2", /*4*/"2", /*5*/"2",
            /*6*/"2", /*7*/"3", /*8*/"3",
            /*9*/"3", /*10*/"Mítico", /*11*/"2"};
    private static String[] NombreMonstruo = {/*0*/"Fénix", /*1*/"Gólem de Piedra", /*2*/"Espíritu del Bosque",
            /*3*/"Dragón de Hielo", /*4*/"Guardián de Hierro", /*5*/"Ángel Guardián",
            /*6*/"Elfo Sanador", /*7*/"Vampiro Ancestral", /*8*/"Espectro Sombrío",
            /*9*/"Nigromante Oscuro", /*10*/"Sombra Tóxica", /*11*/"Guerrero Eterno"};
    private static int[] SaludMonstruo = {/*0*/25, /*1*/40, /*2*/20, /*3*/40, /*4*/60, /*5*/40, /*6*/35, /*7*/60, /*8*/25, /*9*/60, /*10*/40, /*11*/50};

    private static int[] DañoMonstruo = {/*0*/15, /*1*/8, /*2*/8, /*3*/20, /*4*/10, /*5*/10, /*6*/25, /*7*/15, /*8*/10, /*9*/7, /*10*/15, /*11*/16};

    private static int[] AgilidadMoustro = {/*0*/3, /*1*/2, /*2*/5, /*3*/4, /*4*/4, /*5*/4, /*6*/5, /*7*/5, /*8*/7, /*9*/4, /*10*/7, /*11*/4};

    private static int[] CuraciónMonstruo = {/*0*/0, /*1*/0, /*2*/15, /*3*/0, /*4*/0, /*5*/6, /*6*/10, /*7*/0, /*8*/0, /*9*/0, /*10*/0, /*11*/0};
    //private static int[] DefensaMonstruo = {/*1*/0, /*2*/15, /*3*/0, /*4*/0, /*5*/25, /*6*/0, /*7*/0, /*8*/0, /*9*/0, /*10*/0, /*10*/0, /*11*/0};



    private static boolean MonstruoEnCombate = false;
    private static boolean InvocaciónNigro = false;
    private static boolean InvocaciónVampi = false;
    private static boolean InvocaciónSombTox = false;

    private static String NivelInvocado;
    private static String NombreInvocado;
    private static int SaludInvocado;
    private static int DañoInvocado;
    private static int AgilidadInvocado;
    private static int CuraciónInvocado;


    private static int DañoMinEnemigo = 0;
    private static int DañoMaxEnemigo = 0;
    private static int DañoMagicoEnemigo = 0;
    private static boolean DañoMagicoEnemigoSN = false;
    private static boolean EnemigoMagico = false;
    private static double ProbabilidadCriticoEnemigo = 0.0;
    private static double ProbabilidadCriticoJugador = 0.0;
    private static double ProbabilidadCriticoInvocacion = 0.0;
    private static int DañoMinJugador = 0;
    private static int DañoMaxJugador = 0;
    private static int DañoMinInvocacion = 0;
    private static int DañoMaxInvocacion = 0;

    private static boolean modoroot = false;
    private static boolean ClaseGuerrero = false;
    private static boolean ClaseMago = false;
    private static boolean ClaseInvocador = false;
    private static int DañoTotal;




    private static int[] InventarioEnemigo = new int[7];
    private static String NivelEnemigo = "";


    // Enemigos del Bosque
    private static String[][] ImgEB = {
            // Nivel 1
            {/*0*/"enemigoduende.txt",/*1*/"enemigolobosalvaje.txt",/*2*/"enemigogoblin.txt",/*3*/"enemigoratagigante.txt",/*4*/"in.txt",/*5*/"in.txt",/*6*/"enemigoduendeelite.txt",/*7*/"enemigoesqueleto.txt"},
            // Nivel 2
            {/*0*/"enemigogoblin.txt", /*1*/"enemigoorco.txt", /*2*/"enemigoarpia.txt", /*3*/"golempiedra.txt", /*4*/"in.txt",  /*5*/"in.txt", /*6*/"in.txt", /*7*/"in.txt"},
            // Nivel 3
            {/*0*/"enemigoesqueletoguerrero.txt",/*1*/ "enemigoliche.txt", /*2*/"enemigohidra.txt", /*3*/"in.txt",  /*4*/"in.txt",/*5*/ "in.txt", /*6*/"enemigoduendeelite.txt"},
            // Nivel 4 (Jefes)
            {/*0*/"enemigodragonrojo.txt", /*1*/"in.txt", /*2*/"in.txt"}
    };

    // Enemigos del Bosque
    private static String[][] EBosque = {
            // Nivel 1
            {/*0*/"Duende",/*1*/"Lobo Salvaje",/*2*/"Goblin",/*3*/"Rata Gigante",/*4*/"Sapo Gigante",/*5*/"Murciélago",/*6*/"Duende de Elite",/*7*/"Esqueleto"},
            // Nivel 2
            {/*0*/"Goblin", /*1*/"Orco", /*2*/"Arpía", /*3*/"Golem de Piedra", /*4*/"Minotauro",  /*5*/"Cíclope", /*6*/"Espectro de Bosque", /*7*/"Mapache"},
            // Nivel 3
            {/*0*/"Esqueleto Guerrero",/*1*/ "Liche", /*2*/"Hidra", /*3*/"Grifo",  /*4*/"Gárgola",/*5*/ "Basilisco", /*6*/"Duende de Elite"},
            // Nivel 4 (Jefes)
            {/*0*/"Jefe Dragón Rojo", /*1*/"Esqueleto gigante", /*2*/"Espectro rey"}
    };

    // Salud mínima de los enemigos
    private static int[][] MinSaludEB = {
            // Nivel 1
            { /*0*/ 10, /*1*/ 10, /*2*/ 10, /*3*/ 10, /*4*/ 12, /*5*/ 5, /*6*/ 17, /*7*/ 13},
            // Nivel 2
            { /*0*/ 16, /*1*/ 25, /*2*/ 21, /*3*/ 32, /*4*/ 21, /*5*/ 19, /*6*/ 15, /*7*/ 28},
            // Nivel 3
            { /*0*/ 40, /*1*/ 35, /*2*/ 39, /*3*/ 35, /*4*/ 45, /*5*/ 37, /*6*/ 36},
            // Nivel 4 (Jefes)
            { /*0*/ 170, /*1*/ 150, /*2*/ 100}
    };

    // Salud extra de los enemigos
    private static int[][] ExtraSaludEB = {
            // Nivel 1
            { /*0*/10, /*1*/ 12, /*2*/ 12, /*3*/ 10, /*4*/ 15, /*5*/ 10, /*6*/ 10, /*7*/ 9},
            // Nivel 2
            { /*0*/ 10, /*1*/ 10, /*2*/ 10, /*3*/ 13, /*4*/ 11, /*5*/ 9, /*6*/ 9, /*7*/ 15},
            // Nivel 3
            { /*0*/ 20, /*1*/ 20, /*2*/ 20, /*3*/ 20, /*4*/ 20, /*5*/ 20, /*6*/ 20},
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0}
    };

    // Daño mínimo de los enemigos
    private static int[][] MinDañoEB= {
            // Nivel 1
            { /*0*/ 0, /*1*/ 1, /*2*/ 1, /*3*/ 0, /*4*/ 1, /*5*/ 0, /*6*/ 3, /*7*/ 1},
            // Nivel 2
            { /*0*/ 3, /*1*/ 2, /*2*/ 3, /*3*/ 0, /*4*/ 2, /*5*/ 1, /*6*/ 0, /*7*/ 3},
            // Nivel 3
            { /*0*/ 5, /*1*/ 5, /*2*/ 4, /*3*/ 6, /*4*/ 4, /*5*/ 3, /*6*/ 7},
            // Nivel 4 (Jefes)
            { /*0*/ 7, /*1*/ 5, /*2*/ 3}
    };

    // Daño extra de los enemigos
    private static int[][] ExtraDañoEB = {
            // Nivel 1
            { /*0*/ 2, /*1*/ 3, /*2*/ 2, /*3*/ 3, /*4*/ 1, /*5*/ 2, /*6*/ 3, /*7*/ 1},
            // Nivel 2
            { /*0*/ 0, /*1*/ 2, /*2*/ 1, /*3*/ 0, /*4*/ 2, /*5*/ 3, /*6*/ 2, /*7*/ 3},
            // Nivel 3
            { /*0*/ 3, /*1*/ 2, /*2*/ 3, /*3*/ 1, /*4*/ 4, /*5*/ 2, /*6*/ 0},
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0}
    };

    // Daño máximo de los enemigos
    private static int[][] MaxDañoEB = {
            // Nivel 1
            { /*0*/ 4, /*1*/ 3, /*2*/ 4, /*3*/ 4, /*4*/ 5, /*5*/ 4, /*6*/ 5, /*7*/ 4},
            // Nivel 2
            { /*0*/ 5, /*1*/ 6, /*2*/ 5, /*3*/ 8, /*4*/ 3, /*5*/ 4, /*6*/ 5, /*7*/ 4},
            // Nivel 3
            { /*0*/ 4, /*1*/ 4, /*2*/ 2, /*3*/3, /*4*/ 6, /*5*/ 4, /*6*/ 7},
            // Nivel 4 (Jefes)
            { /*0*/ 11, /*1*/ 9, /*2*/ 7}
    };

    // Agilidad de los enemigos
    private static int[][] AgilidadEB= {
            // Nivel 1
            { /*0*/ 2, /*1*/ 3, /*2*/ 2, /*3*/ 2, /*4*/ 1, /*5*/ 5, /*6*/ 3, /*7*/ 2},
            // Nivel 2
            { /*0*/ 3, /*1*/ 3, /*2*/ 4, /*3*/ 3, /*4*/ 3, /*5*/ 2, /*6*/ 7, /*7*/ 4},
            // Nivel 3
            { /*0*/ 3, /*1*/ 4, /*2*/ 3, /*3*/ 5, /*4*/ 3, /*5*/ 3, /*6*/ 4},
            // Nivel 4 (Jefes)
            { /*0*/ 6, /*1*/ 4, /*2*/ 7}
    };

    // Probabilidad de crítico de los enemigos
    private static double[][] PCriticoEB = {
            // Nivel 1
            { /*0*/ 0.09, /*1*/ 0.19, /*2*/ 0.10, /*3*/ 0.12, /*4*/ 0.10, /*5*/ 0.16, /*6*/ 0.20, /*7*/ 0.14 },
            // Nivel 2
            { /*0*/ 0.19, /*1*/ 0.25, /*2*/ 0.27, /*3*/ 0.39, /*4*/ 0.24, /*5*/ 0.24, /*6*/ 0.25, /*7*/ 0.29 },
            // Nivel 3
            { /*0*/ 0.28, /*1*/ 0.30, /*2*/ 0.31, /*3*/ 0.39, /*4*/ 0.35, /*5*/ 0.29, /*6*/ 0.35 },
            // Nivel 4 (Jefes)
            { /*0*/ 0.25, /*1*/ 0.27, /*2*/ 0.15 }
    };

    // Magia de los enemigos (si es mágico o no)
    private static boolean[][] PMagicoEB = {
            // Nivel 1
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false, /*4*/ false, /*5*/ false, /*6*/ false, /*7*/ false },
            // Nivel 2
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false, /*4*/ false, /*5*/ false, /*6*/ false, /*7*/ false },
            // Nivel 3
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false, /*4*/ false, /*5*/ false, /*6*/ false },
            // Nivel 4 (Jefes)
            { /*0*/ false, /*1*/ false, /*2*/ false }
    };

    // Probabilidad de crítico de los enemigos
    private static int[][] DañoMagicoEB = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0},
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0},
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0}
    };




    private static String[][] ImgEM = {
            // Nivel 1
            {/*0*/"in.txt",/*1*/"in.txt",/*2*/"in.txt",/*3*/"in.txt"},
            // Nivel 2
            {/*0*/"enemigogolemlava.txt", /*1*/"fenix.txt", /*2*/"in.txt"},
            // Nivel 3
            {/*0*/"in.txt",/*1*/ "in.txt"},
            // Nivel 4 (Jefes)
            {/*0*/"in.txt"}
    };

    // Enemigos de Magma
    private static String[][] EMagma = {
            // Nivel 1
            {/*0*/"Rata de Fuego",  /*1*/"Serpiente",  /*2*/"Lobo de lava", /*3*/"Duende de Sombras"},
            // Nivel 2
            {/*0*/"Golem de Lava",  /*1*/"Fénix",  /*2*/"Demonio pequeño"},
            // Nivel 3
            {/*0*/"Tiburón de Lava",  /*1*/"Grifo"},
            // Nivel 4 (Jefes)
            {/*0*/"Lord de la Lava"}
    };

    // Salud mínima de los enemigos
    private static int[][] MinSaludEM = {
            // Nivel 1
            { /*0*/ 15, /*1*/ 20, /*2*/ 15, /*3*/ 10},
            // Nivel 2
            { /*0*/ 36, /*1*/ 25, /*2*/ 25},
            // Nivel 3
            { /*0*/ 40, /*1*/ 35},
            // Nivel 4 (Jefes)
            { /*0*/ 150}
    };

    // Salud extra de los enemigos
    private static int[][] ExtraSaludEM = {
            // Nivel 1
            { /*0*/ 10, /*1*/ 5, /*2*/ 10, /*3*/ 10},
            // Nivel 2
            { /*0*/ 10, /*1*/ 7, /*2*/ 5},
            // Nivel 3
            { /*0*/ 20, /*1*/ 15},
            // Nivel 4 (Jefes)
            { /*0*/ 0}
    };

    // Daño mínimo de los enemigos
    private static int[][] MinDañoEM = {
            // Nivel 1
            { /*0*/ 1, /*1*/ 2, /*2*/ 3, /*3*/ 4},
            // Nivel 2
            { /*0*/ 2, /*1*/ 5, /*2*/ 4},
            // Nivel 3
            { /*0*/ 7, /*1*/ 7},
            // Nivel 4 (Jefes)
            { /*0*/ 11}
    };

    // Daño extra de los enemigos
    private static int[][] ExtraDañoEM = {
            // Nivel 1
            { /*0*/ 1, /*1*/ 1, /*2*/ 0, /*3*/ 1},
            // Nivel 2
            { /*0*/ 2, /*1*/ 2, /*2*/ 1},
            // Nivel 3
            { /*0*/ 1, /*1*/ 2},
            // Nivel 4 (Jefes)
            { /*0*/ 0}
    };

    // Daño máximo de los enemigos
    private static int[][] MaxDañoEM = {
            // Nivel 1
            { /*0*/ 5, /*1*/ 5, /*2*/ 7, /*3*/ 4},
            // Nivel 2
            { /*0*/ 5, /*1*/ 5, /*2*/ 6},
            // Nivel 3
            { /*0*/ 9, /*1*/ 8},
            // Nivel 4 (Jefes)
            { /*0*/ 10}
    };

    // Agilidad de los enemigos
    private static int[][] AgilidadEM = {
            // Nivel 1
            { /*0*/ 2, /*1*/ 5, /*2*/ 3, /*3*/ 6},
            // Nivel 2
            { /*0*/ 2, /*1*/ 4, /*2*/ 3},
            // Nivel 3
            { /*0*/ 3, /*1*/ 5},
            // Nivel 4 (Jefes)
            { /*0*/ 4}
    };

    // Probabilidad de crítico de los enemigos
    private static double[][] PCriticoEM = {
            // Nivel 1
            { /*0*/ 0.14, /*1*/ 0.20, /*2*/ 0.17, /*3*/ 0.25},
            // Nivel 2
            { /*0*/ 0.25, /*1*/ 0.21, /*2*/ 0.29},
            // Nivel 3
            { /*0*/ 0.35, /*1*/ 0.32},
            // Nivel 4 (Jefes)
            { /*0*/ 0.19}
    };

    // Magia de los enemigos (si es mágico o no)
    private static boolean[][] PMagicoEM = {
            // Nivel 1
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ true },
            // Nivel 2
            { /*0*/ false, /*1*/ false, /*2*/ true },
            // Nivel 3
            { /*0*/ false, /*1*/ false},
            // Nivel 4 (Jefes)
            { /*0*/ false }
    };

    // Probabilidad de crítico de los enemigos
    private static int[][] DañoMagicoEM = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 2},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 4},
            // Nivel 3
            { /*0*/ 0, /*1*/ 0},
            // Nivel 4 (Jefes)
            { /*0*/ 0}
    };



    // Enemigos del Bosque
    private static String[][] ImgEG = {
            // Nivel 1
            {/*0*/"in.txt",/*1*/"enemigogolemgemas.txt",/*2*/"in.txt",/*3*/"in.txt"},
            // Nivel 2
            {/*0*/"in.txt", /*1*/"fenix.txt", /*2*/"in.txt"},
            // Nivel 3
            {/*0*/"in.txt",/*1*/ "in.txt"},
            // Nivel 4 (Jefes)
            {/*0*/"in.txt"}
    };
    // Enemigos de Gemas
    private static String[][] EGemas = {
            // Nivel 1
            {/*0*/"Rata de Cristal",  /*1*/ "Golemita de Gemas", /*2*/"Serpiente de Rubí", /*3*/ "Espectro de Esmeralda"},
            // Nivel 2
            {/*0*/"Golem de Zafiro",  /*1*/ "Fénix de Diamante", /*2*/"Elfo de Rubí"},
            // Nivel 3
            {/*0*/"Quimera de Cristal",  /*1*/ "Basilisco de Zafiro", /*2*/"Gárgola de Esmeralda"},
            // Nivel 4 (Jefes)
            {/*0*/"Dragón de Diamante",  /*1*/ "Señor de las Gemas"}
    };

    // Salud mínima de los enemigos
    private static int[][] MinSaludEG = {
            // Nivel 1
            { /*0*/ 17, /*1*/ 25, /*2*/ 20, /*3*/ 17},
            // Nivel 2
            { /*0*/ 45, /*1*/ 45, /*2*/ 45},
            // Nivel 3
            { /*0*/ 60, /*1*/ 60, /*2*/ 60},
            // Nivel 4 (Jefes)
            { /*0*/ 200, /*1*/ 200}
    };

    // Salud extra de los enemigos
    private static int[][] ExtraSaludEG = {
            // Nivel 1
            { /*0*/ 20, /*1*/ 20, /*2*/ 20, /*3*/ 20},
            // Nivel 2
            { /*0*/ 20, /*1*/ 20, /*2*/ 20},
            // Nivel 3
            { /*0*/ 20, /*1*/ 20, /*2*/ 20},
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0}
    };

    // Daño mínimo de los enemigos
    private static int[][] MinDañoEG = {
            // Nivel 1
            { /*0*/ 1, /*1*/ 1, /*2*/ 1, /*3*/ 1},
            // Nivel 2
            { /*0*/ 3, /*1*/ 3, /*2*/ 3},
            // Nivel 3
            { /*0*/ 5, /*1*/ 5, /*2*/ 5},
            // Nivel 4 (Jefes)
            { /*0*/ 7, /*1*/ 7}
    };

    // Daño extra de los enemigos
    private static int[][] ExtraDañoEG = {
            // Nivel 1
            { /*0*/ 2, /*1*/ 2, /*2*/ 2, /*3*/ 2},
            // Nivel 2
            { /*0*/ 2, /*1*/ 2, /*2*/ 2},
            // Nivel 3
            { /*0*/ 3, /*1*/ 3, /*2*/ 3},
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0}
    };

    // Daño máximo de los enemigos
    private static int[][] MaxDañoEG = {
            // Nivel 1
            { /*0*/ 4, /*1*/ 4, /*2*/ 4, /*3*/ 4},
            // Nivel 2
            { /*0*/ 4, /*1*/ 4, /*2*/ 4},
            // Nivel 3
            { /*0*/ 4, /*1*/ 4, /*2*/ 4},
            // Nivel 4 (Jefes)
            { /*0*/ 6, /*1*/ 6}
    };

    // Agilidad de los enemigos
    private static int[][] AgilidadEG = {
            // Nivel 1
            { /*0*/ 4, /*1*/ 2, /*2*/ 3, /*3*/ 3},
            // Nivel 2
            { /*0*/ 3, /*1*/ 3, /*2*/ 3},
            // Nivel 3
            { /*0*/ 4, /*1*/ 4, /*2*/ 4},
            // Nivel 4 (Jefes)
            { /*0*/ 4, /*1*/ 4}
    };

    // Probabilidad de crítico de los enemigos
    private static double[][] PCriticoEG = {
            // Nivel 1
            { /*0*/ 0.19, /*1*/ 0.19, /*2*/ 0.18, /*3*/ 0.19},
            // Nivel 2
            { /*0*/ 0.21, /*1*/ 0.21, /*2*/ 0.21},
            // Nivel 3
            { /*0*/ 0.26, /*1*/ 0.26, /*2*/ 0.25},
            // Nivel 4 (Jefes)
            { /*0*/ 0.32, /*1*/ 0.32}
    };

    // Magia de los enemigos (si es mágico o no)
    private static boolean[][] PMagicoEG = {
            // Nivel 1
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false},
            // Nivel 2
            { /*0*/ false, /*1*/ false, /*2*/ false },
            // Nivel 3
            { /*0*/ false, /*1*/ false, /*2*/ false },
            // Nivel 4 (Jefes)
            { /*0*/ false, /*1*/ false}
    };

    // Probabilidad de crítico de los enemigos
    private static int[][] DañoMagicoEG = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0},
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0},
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0}
    };



    private static String[][] ImgEEl = {
            // Nivel 1
            {/*0*/"in.txt",/*1*/"in.txt",/*2*/"in.txt",/*3*/"in.txt"},
            // Nivel 2
            {/*0*/"enemigogolemlava.txt", /*1*/"fenix.txt", /*2*/"in.txt"},
            // Nivel 3
            {/*0*/"in.txt",/*1*/ "in.txt"},
            // Nivel 4 (Jefes)
            {/*0*/"in.txt"}
    };
    // Enemigos de Elfos
    private static String[][] EElfos = {
            // Nivel 1
            {/*0*/"Elfo Silvano",/*1*/ "Centinela Elfo",/*2*/ "Guardabosques", /*3*/"Druida", /*4*/"Elfo joven"},
            // Nivel 2
            {/*0*/"Arquero Elfo", /*1*/"Hechicero Elfo", /*2*/"Guerrero Elfo"},
            // Nivel 3
            {/*0*/"Mago Elfo", /*1*/"Paladín Elfo", /*2*/"Chamán Elfo"},
            // Nivel 4 (Jefes)
            {/*0*/"Rey Elfo", /*1*/"Reina Elfa"}
    };

    // Salud mínima de los enemigos
    private static int[][] MinSaludEEl = {
            // Nivel 1
            { /*0*/ 40, /*1*/ 40, /*2*/ 40, /*3*/ 40, /*4*/ 55},
            // Nivel 2
            { /*0*/ 70, /*1*/ 65, /*2*/ 80},
            // Nivel 3
            { /*0*/ 120, /*1*/ 150, /*2*/ 120,},
            // Nivel 4 (Jefes)
            { /*0*/ 300, /*1*/ 225 }
    };

    // Salud extra de los enemigos
    private static int[][] ExtraSaludEEl = {
            // Nivel 1
            { /*0*/ 10, /*1*/ 15, /*2*/ 10, /*3*/ 17, /*4*/ 0},
            // Nivel 2
            { /*0*/ 20, /*1*/ 15, /*2*/ 20},
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0},
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0 }
    };

    // Daño mínimo de los enemigos
    private static int[][] MinDañoEEl = {
            // Nivel 1
            { /*0*/ 3, /*1*/ 4, /*2*/ 3, /*3*/ 4, /*4*/ 5 },
            // Nivel 2
            { /*0*/ 7, /*1*/ 10, /*2*/ 11 },
            // Nivel 3
            { /*0*/ 15, /*1*/ 14, /*2*/ 12 },
            // Nivel 4 (Jefes)
            { /*0*/ 17, /*1*/ 12 }
    };

    // Daño extra de los enemigos
    private static int[][] ExtraDañoEEl = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0 },
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0 }
    };

    // Daño máximo de los enemigos
    private static int[][] MaxDañoEEl = {
            // Nivel 1
            { /*0*/ 10, /*1*/ 9, /*2*/ 11, /*3*/ 7, /*4*/ 10 },
            // Nivel 2
            { /*0*/ 10, /*1*/ 10, /*2*/ 10 },
            // Nivel 3
            { /*0*/ 10, /*1*/ 10, /*2*/ 10 },
            // Nivel 4 (Jefes)
            { /*0*/ 10, /*1*/ 10 }
    };

    // Agilidad de los enemigos
    private static int[][] AgilidadEEl = {
            // Nivel 1
            { /*0*/ 2, /*1*/ 3, /*2*/ 3, /*3*/ 4, /*4*/ 4 },
            // Nivel 2
            { /*0*/ 4, /*1*/ 3, /*2*/ 3 },
            // Nivel 3
            { /*0*/ 4, /*1*/ 4, /*2*/ 4 },
            // Nivel 4 (Jefes)
            { /*0*/ 4, /*1*/ 3 }
    };

    // Probabilidad de crítico de los enemigos
    private static double[][] PCriticoEEl = {
            // Nivel 1
            { /*0*/ 0.11, /*1*/ 0.12, /*2*/ 0.16, /*3*/ 0.12, /*4*/ 0.19 },
            // Nivel 2
            { /*0*/ 0.21, /*1*/ 0.10, /*2*/ 0.30 },
            // Nivel 3
            { /*0*/ 0.27, /*1*/ 0.21, /*2*/ 0.26 },
            // Nivel 4 (Jefes)
            { /*0*/ 0.15, /*1*/ 0.19 }
    };

    // Magia de los enemigos (si es mágico o no)
    private static boolean[][] PMagicoEEl = {
            // Nivel 1
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false, /*4*/ false},
            // Nivel 2
            { /*0*/ false, /*1*/ false, /*2*/ false},
            // Nivel 3
            { /*0*/ false, /*1*/ false, /*2*/ false},
            // Nivel 4 (Jefes)
            { /*0*/ false, /*1*/ false }
    };

    // Probabilidad de crítico de los enemigos
    private static int[][] DañoMagicoEEl = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0 },
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0 }
    };






    private static String[][] ImgEC = {
            // Nivel 1
            {/*0*/"in.txt",/*1*/"in.txt",/*2*/"in.txt",/*3*/"in.txt"},
            // Nivel 2
            {/*0*/"enemigogolemlava.txt", /*1*/"fenix.txt", /*2*/"in.txt"},
            // Nivel 3
            {/*0*/"in.txt",/*1*/ "in.txt"},
            // Nivel 4 (Jefes)
            {/*0*/"in.txt"}
    };

    // Enemigos de Castillo
    private static String[][] ECastillo = {
            // Nivel 1
            {/*0*/"Bandido", /*1*/"Soldado", /*2*/"Arbalestero", /*3*/"Caballero", /*4*/"Guardia"},
            // Nivel 2
            {/*0*/"Bandido", /*1*/"Ballestero", /*2*/"Lancero"},
            // Nivel 3
            {/*0*/"Comandante", /*1*/"Paladín", /*2*/"Mago Real"},
            // Nivel 4 (Jefes)
            {/*0*/"Gran General"}
    };

    // Salud mínima de los enemigos
    private static int[][] MinSaludEC = {
            // Nivel 1
            { /*0*/ 25, /*1*/ 35, /*2*/ 20, /*3*/ 25, /*4*/ 30},
            // Nivel 2
            { /*0*/ 55, /*1*/ 40, /*2*/ 40 },
            // Nivel 3
            { /*0*/ 70, /*1*/ 58, /*2*/ 60 },
            // Nivel 4 (Jefes)
            { /*0*/ 130 }
    };

    // Salud extra de los enemigos
    private static int[][] ExtraSaludEC = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 15, /*2*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0 }
    };

    // Daño mínimo de los enemigos
    private static int[][] MinDañoEC = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0 }
    };

    // Daño extra de los enemigos
    private static int[][] ExtraDañoEC = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0 }
    };

    // Daño máximo de los enemigos
    private static int[][] MaxDañoEC = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0 }

    };

    // Agilidad de los enemigos
    private static int[][] AgilidadEC = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0 }
    };

    // Probabilidad de crítico de los enemigos
    private static double[][] PCriticoEC = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0 }
    };

    // Magia de los enemigos (si es mágico o no)
    private static boolean[][] PMagicoEC = {
            // Nivel 1
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false, /*4*/ false },
            // Nivel 2
            { /*0*/ false, /*1*/ false, /*2*/ false },
            // Nivel 3
            { /*0*/ false, /*1*/ false, /*2*/ false },
            // Nivel 4 (Jefes)
            { /*0*/ false }
    };

    // Probabilidad de crítico de los enemigos
    private static int[][] DañoMagicoEC = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0},
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0 }
    };




    // Enemigos Especiales
    private static String[][] EEspeciales = {
            // Nivel 1
            {"Fantasma", "Sombrío", "Espectro de Fuego"},
            // Nivel 2
            {"Nigromante", "Bestia Oscura", "Guardián del Abismo"},
            // Nivel 3
            {"Liche Rey", "Señor Oscuro", "Demonio Ancestral"},
            // Nivel 4 (Jefes)
            {"Cthulhu", "Rey Demonio", "Titán del Caos"}
    };
    // Salud mínima de los enemigos
    private static int[][] MinSaludEEs = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0 }
    };

    // Salud extra de los enemigos
    private static int[][] ExtraSaludEEs = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0 }
    };

    // Daño mínimo de los enemigos
    private static int[][] MinDañoEEs = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0 }
    };

    // Daño extra de los enemigos
    private static int[][] ExtraDañoEEs = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0 }
    };

    // Daño máximo de los enemigos
    private static int[][] MaxDañoEEs = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0 }
    };

    // Agilidad de los enemigos
    private static int[][] AgilidadEEs = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0 }
    };

    // Probabilidad de crítico de los enemigos
    private static double[][] PCriticoEEs = {
            // Nivel 1
            { /*0*/ 0.0, /*1*/ 0.0, /*2*/ 0.0, /*3*/ 0.0, /*4*/ 0.0, /*5*/ 0.0, /*6*/ 0.0, /*7*/ 0.0, /*8*/ 0.0, /*9*/ 0.0, /*10*/ 0.0, /*11*/ 0.0, /*12*/ 0.0, /*13*/ 0.0, /*14*/ 0.0, /*15*/ 0.0, /*16*/ 0.0, /*17*/ 0.0, /*18*/ 0.0, /*19*/ 0.0, /*20*/ 0.0, /*21*/ 0.0, /*22*/ 0.0 },
            // Nivel 2
            { /*0*/ 0.0, /*1*/ 0.0, /*2*/ 0.0, /*3*/ 0.0, /*4*/ 0.0, /*5*/ 0.0, /*6*/ 0.0, /*7*/ 0.0, /*8*/ 0.0, /*9*/ 0.0, /*10*/ 0.0, /*11*/ 0.0, /*12*/ 0.0, /*13*/ 0.0, /*14*/ 0.0, /*15*/ 0.0, /*16*/ 0.0, /*17*/ 0.0, /*18*/ 0.0, /*19*/ 0.0, /*20*/ 0.0, /*21*/ 0.0, /*22*/ 0.0 },
            // Nivel 3
            { /*0*/ 0.0, /*1*/ 0.0, /*2*/ 0.0, /*3*/ 0.0, /*4*/ 0.0, /*5*/ 0.0, /*6*/ 0.0, /*7*/ 0.0, /*8*/ 0.0, /*9*/ 0.0, /*10*/ 0.0, /*11*/ 0.0, /*12*/ 0.0, /*13*/ 0.0, /*14*/ 0.0, /*15*/ 0.0, /*16*/ 0.0, /*17*/ 0.0, /*18*/ 0.0, /*19*/ 0.0, /*20*/ 0.0, /*21*/ 0.0, /*22*/ 0.0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0.0, /*1*/ 0.0, /*2*/ 0.0, /*3*/ 0.0, /*4*/ 0.0, /*5*/ 0.0 }
    };

    // Magia de los enemigos (si es mágico o no)
    private static boolean[][] PMagicoEEs = {
            // Nivel 1
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false, /*4*/ false, /*5*/ false, /*6*/ false, /*7*/ false, /*8*/ false, /*9*/ false, /*10*/ false, /*11*/ false, /*12*/ false, /*13*/ false, /*14*/ false, /*15*/ false, /*16*/ false, /*17*/ false, /*18*/ false, /*19*/ false, /*20*/ false, /*21*/ false, /*22*/ false },
            // Nivel 2
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false, /*4*/ false, /*5*/ false, /*6*/ false, /*7*/ false, /*8*/ false, /*9*/ false, /*10*/ false, /*11*/ false, /*12*/ false, /*13*/ false, /*14*/ false, /*15*/ false, /*16*/ false, /*17*/ false, /*18*/ false, /*19*/ false, /*20*/ false, /*21*/ false, /*22*/ false },
            // Nivel 3
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false, /*4*/ false, /*5*/ false, /*6*/ false, /*7*/ false, /*8*/ false, /*9*/ false, /*10*/ false, /*11*/ false, /*12*/ false, /*13*/ false, /*14*/ false, /*15*/ false, /*16*/ false, /*17*/ false, /*18*/ false, /*19*/ false, /*20*/ false, /*21*/ false, /*22*/ false },
            // Nivel 4 (Jefes)
            { /*0*/ false, /*1*/ false, /*2*/ false, /*3*/ false, /*4*/ false, /*5*/ false }
    };

    // Probabilidad de crítico de los enemigos
    private static int[][] DañoMagicoEEs = {
            // Nivel 1
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 2
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 3
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0, /*6*/ 0, /*7*/ 0, /*8*/ 0, /*9*/ 0, /*10*/ 0, /*11*/ 0, /*12*/ 0, /*13*/ 0, /*14*/ 0, /*15*/ 0, /*16*/ 0, /*17*/ 0, /*18*/ 0, /*19*/ 0, /*20*/ 0, /*21*/ 0, /*22*/ 0 },
            // Nivel 4 (Jefes)
            { /*0*/ 0, /*1*/ 0, /*2*/ 0, /*3*/ 0, /*4*/ 0, /*5*/ 0 }
    };


























    //--------------------------------------------------------------------------------------------------------------------------
    private static String[] ImgEnemigoN1 =
            {/*0*/"enemigoduende.txt", /*1*/"enemigolobosalvaje.txt", /*2*/"enemigogoblin.txt",/*3*/"enemigoratagigante.txt", /*4*/"in.txt", /*5*/"in.txt",
                    /*6*/"enemigoduendeelite.txt", /*7*/"in.txt", /*8*/"in.txt",
                    /*9*/"in.txt", /*10*/"in.txt", /*11*/"in.txt",/*12*/"in.txt",
                    /*13*/"in.txt", /*14*/"in.txt",/*15*/"in.txt", /*16*/"in.txt", /*17*/"in.txt",/*18*/"in.txt", /*19*/"in.txt", /*20*/"in.txt",/*21*/"in.txt", /*22*/"in.txt"};

    private static String[] NombreEnemigoN1 = {
            /*0*/"Duende", /*1*/"Lobo Salvaje", /*2*/"Goblin", /*3*/"Rata Gigante", /*4*/"Sapo Gigante", /*5*/"Murciélago",
            /*6*/"Duende de Elite", /*7*/"Espectro de Bosque",/*8*/"Goblin Berserker",
            /*9*/"Rata de Fuego", /*10*/"Serpiente", /*11*/"Lobo de lava", /*12*/"Duende de Sombras",
            /*13*/"Rata de Acero", /*14*/"", /*15*/"",/*16*/"", /*17*/"", /*18*/"", /*19*/"", /*20*/"",/*21*/"", /*22*/""
    };

    // Completar los atributos para los enemigos restantes en el Nivel N1:
    private static int[] MinSaludEnemigoN1 = {
            /*0*/10, /*1*/15, /*2*/15, /*3*/10, /*4*/12, /*5*/8,
            /*6*/15, /*7*/10, /*8*/20,
            /*9*/18, /*10*/12, /*11*/16,/*12*/18,
            /*13*/22, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static int[] ExtraSaludEnemigoN1 = {
            /*0*/15, /*1*/15, /*2*/15, /*3*/10, /*4*/18, /*5*/12,
            /*6*/25, /*7*/5, /*8*/25,
            /*9*/20, /*10*/18, /*11*/22,/*12*/25,
            /*13*/18, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static int[] MinDañoEnemigoN1 = {
            /*0*/1, /*1*/2, /*2*/1, /*3*/2, /*4*/2, /*5*/1,
            /*6*/3, /*7*/1, /*8*/3,
            /*9*/2, /*10*/3, /*11*/5,/*12*/3,
            /*13*/0, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static int[] ExtraDañoEnemigoN1 = {
            /*0*/3, /*1*/2, /*2*/3, /*3*/2, /*4*/2, /*5*/0,
            /*6*/3, /*7*/1, /*8*/2,
            /*9*/2,/*10*/2, /*11*/6,/*12*/7,
            /*13*/2, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static int[] MaxDañoEnemigoN1 = {
            /*0*/5, /*1*/5, /*2*/5, /*3*/5, /*4*/6, /*5*/5,
            /*6*/5, /*7*/6, /*8*/7,
            /*9*/6, /*10*/6, /*11*/7,/*12*/8,
            /*13*/7, /*14*/9, /*15*/8, /*16*/10, /*17*/9,/*18*/10, /*19*/9, /*20*/11, /*21*/11, /*22*/12
    };

    private static int[] AgilidadEnemigoN1 = {
            /*0*/2, /*1*/3, /*2*/2, /*3*/3, /*4*/3, /*5*/5,
            /*6*/4, /*7*/6, /*8*/3,
            /*9*/3, /*10*/4, /*11*/3,/*12*/3,
            /*13*/2, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static double[] PCriticoEnemigoN1 = {
            /*0*/0.09, /*1*/0.11, /*2*/0.10, /*3*/0.08, /*4*/0.08, /*5*/0.11,
            /*6*/0.21, /*7*/0.10, /*8*/0.15,
            /*9*/0.13, /*10*/0.12, /*11*/0.14,/*12*/0.17,
            /*13*/0.14, /*14*/0.0, /*15*/0.0, /*16*/0.0, /*17*/0.0,/*18*/0.0, /*19*/0.0, /*20*/0.0, /*21*/0.0, /*22*/0.0
    };


    //---------------------------------------------------------------------------------------
    private static String[] ImgEnemigoN2 = {
            /*0*/"enemigoesqueleto.txt", /*1*/"enemigoorco.txt", /*2*/"enemigoarpia.txt",/*3*/"golempiedra.txt", /*4*/"in.txt", /*5*/"in.txt",
            /*6*/"enemigobandido.txt", /*7*/"enemigoelfojoven.txt", /*8*/"in.txt",
            /*9*/"enemigogolemlava.txt", /*10*/"fenix.txt", /*11*/"in.txt",
            /*12*/"in.txt", /*13*/"in.txt", /*14*/"in.txt",/*15*/"in.txt", /*16*/"in.txt", /*17*/"in.txt",/*18*/"in.txt", /*19*/"in.txt", /*20*/"in.txt",/*21*/"in.txt", /*22*/"in.txt"};
    private static String[] NombreEnemigoN2 = {
            /*0*/"Esqueleto", /*1*/"Orco", /*2*/"Arpía", /*3*/"Golem de Piedra", /*4*/"Minotauro", /*5*/"Cíclope",
            /*6*/"Bandido", /*7*/"Elfo joven", /*8*/"Goblin Gigante",
            /*9*/"Golem de Lava", /*10*/"Fénix",/*11*/"Demonio pequeño",
            /*12*/"",/*13*/"", /*14*/"", /*15*/"",/*16*/"", /*17*/"", /*18*/"", /*19*/"", /*20*/"",/*21*/"",/*22*/ ""
    };
    private static int[] MinSaludEnemigoN2 = {
            /*0*/15, /*1*/20, /*2*/15, /*3*/30, /*4*/20, /*5*/18,
            /*6*/45, /*7*/50, /*8*/45,
            /*9*/25, /*10*/20, /*11*/15,
            /*12*/0, /*13*/0, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static int[] ExtraSaludEnemigoN2 = {
            /*0*/10, /*1*/15, /*2*/15, /*3*/20, /*4*/10, /*5*/20,
            /*6*/25, /*7*/20, /*8*/15,
            /*9*/20, /*10*/10, /*11*/15,
            /*12*/0, /*13*/0, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static int[] MinDañoEnemigoN2 = {
            /*0*/3, /*1*/5, /*2*/4, /*3*/0, /*4*/4, /*5*/5,
            /*6*/6, /*7*/5, /*8*/4,
            /*9*/0, /*10*/5, /*11*/3,
            /*12*/0, /*13*/0, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static int[] ExtraDañoEnemigoN2 = {
            /*0*/2, /*1*/0, /*2*/1, /*3*/0, /*4*/1, /*5*/2,
            /*6*/0, /*7*/0, /*8*/2,
            /*9*/1, /*10*/3, /*11*/1,
            /*12*/0, /*13*/0, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static int[] MaxDañoEnemigoN2 = {
            /*0*/6, /*1*/5, /*2*/5, /*3*/15, /*4*/5, /*5*/5,
            /*6*/10, /*7*/10, /*8*/9,
            /*9*/7, /*10*/10, /*11*/7,
            /*12*/0, /*13*/0, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static int[] AgilidadEnemigoN2 = {
            /*0*/2, /*1*/3, /*2*/4, /*3*/3, /*4*/3, /*5*/4,
            /*6*/4, /*7*/4, /*8*/4,
            /*9*/3, /*10*/5, /*11*/3,
            /*12*/0, /*13*/0, /*14*/0, /*15*/0, /*16*/0, /*17*/0,
            /*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static double[] PCriticoEnemigoN2 = {
            /*0*/0.15, /*1*/0.17, /*2*/0.20, /*3*/0.27, /*4*/0.18, /*5*/0.20,
            /*6*/0.21, /*7*/0.21, /*8*/0.19,
            /*9*/0.21, /*10*/0.21, /*11*/0.23,
            /*12*/0, /*13*/0, /*14*/0, /*15*/0, /*16*/0, /*17*/0,
            /*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0
    };

    private static boolean[] PMagicoEnemigoN2 = {
            /*0*/false,/*1*/false,/*2*/false,/*3*/false,/*4*/false,/*5*/false,
            /*6*/true,/*7*/true,/*8*/false,
            /*9*/false,/*10*/false,/*11*/true,
            /*12*/false,/*13*/false,/*14*/false,/*15*/false,/*16*/false,/*17*/false,/*18*/false,/*19*/false,/*20*/false,/*21*/false,/*22*/false};
    private static int[] DañoMagicoEnemigoN2 = {
            /*0*/0, /*1*/0, /*2*/0, /*3*/0, /*4*/0, /*5*/0,
            /*6*/5, /*7*/5, /*8*/0,
            /*9*/0, /*10*/0, /*11*/7,
            /*12*/0, /*13*/0, /*14*/0, /*15*/0, /*16*/0, /*17*/0,/*18*/0, /*19*/0, /*20*/0, /*21*/0, /*22*/0};

    //---------------------------------------------------------------------------------------
    private static String[] ImgEnemigoN3 =
            {/*0*/"enemigoesqueletoguerrero.txt", /*1*/"enemigoliche.txt", /*2*/"enemigodemoniomayor.txt",/*3*/"enemigohidra.txt", /*4*/"in.txt", /*5*/"in.txt",
                    /*6*/"in.txt", /*7*/"in.txt", /*8*/"in.txt",
                    /*9*/"in.txt", /*10*/"in.txt",
                    /*11*/"in.txt",/*12*/"in.txt", /*13*/"in.txt", /*14*/"in.txt",/*15*/"in.txt", /*16*/"in.txt", /*17*/"in.txt",/*18*/"in.txt", /*19*/"in.txt", /*20*/"in.txt",/*21*/"in.txt", /*22*/"in.txt"};
    private static String[] NombreEnemigoN3 = {
            /*0*/"Esqueleto Guerrero", /*1*/"Liche", /*2*/"Demonio Mayor", /*3*/"Hidra", /*4*/"Grifo", /*5*/"Vampiro",
            /*6*/"Gárgola", /*7*/"Basilisco", /*8*/"Quimera",
            /*9*/"Tiburón de Lava", /*10*/"Grifo",
            /*11*/"", /*12*/"", /*13*/"", /*14*/"", /*15*/"",/*16*/"", /*17*/"", /*18*/"", /*19*/"", /*20*/"",/*21*/"", /*22*/""
    };
    private static int[] MinSaludEnemigoN3 = {
            /*0*/60, /*1*/60, /*2*/60, /*3*/60, /*4*/65, /*5*/65,
            /*6*/70, /*7*/70, /*8*/68, /*9*/72, /*10*/74, /*11*/72,
            /*12*/65, /*13*/75, /*14*/80, /*15*/78, /*16*/74, /*17*/72,
            /*18*/78, /*19*/80, /*20*/85, /*21*/90, /*22*/95
    };

    private static int[] ExtraSaludEnemigoN3 = {
            /*0*/25, /*1*/25, /*2*/25, /*3*/25, /*4*/30, /*5*/25,
            /*6*/35, /*7*/35, /*8*/32, /*9*/30, /*10*/28, /*11*/30,
            /*12*/20, /*13*/35, /*14*/40, /*15*/38, /*16*/30, /*17*/30,
            /*18*/35, /*19*/35, /*20*/40, /*21*/42, /*22*/45
    };

    private static int[] MinDañoEnemigoN3 = {
            /*0*/4, /*1*/4, /*2*/4, /*3*/4, /*4*/6, /*5*/5,
            /*6*/5, /*7*/5, /*8*/6, /*9*/7, /*10*/6, /*11*/7,
            /*12*/3, /*13*/8, /*14*/10, /*15*/9, /*16*/7, /*17*/6,
            /*18*/8, /*19*/9, /*20*/10, /*21*/11, /*22*/12
    };

    private static int[] ExtraDañoEnemigoN3 = {
            /*0*/5, /*1*/5, /*2*/5, /*3*/5, /*4*/6, /*5*/5,
            /*6*/6, /*7*/6, /*8*/7, /*9*/7, /*10*/6, /*11*/7,
            /*12*/3, /*13*/8, /*14*/9, /*15*/8, /*16*/7, /*17*/7,
            /*18*/8, /*19*/8, /*20*/10, /*21*/11, /*22*/12
    };

    private static int[] MaxDañoEnemigoN3 = {
            /*0*/8, /*1*/8, /*2*/8, /*3*/8, /*4*/9, /*5*/8,
            /*6*/10, /*7*/10, /*8*/9, /*9*/10, /*10*/10, /*11*/11,
            /*12*/0, /*13*/12, /*14*/13, /*15*/12, /*16*/11, /*17*/10,
            /*18*/12, /*19*/13, /*20*/14, /*21*/15, /*22*/16
    };

    private static int[] AgilidadEnemigoN3 = {
            /*0*/5, /*1*/5, /*2*/5, /*3*/5, /*4*/6, /*5*/5,
            /*6*/4, /*7*/4, /*8*/5, /*9*/6, /*10*/5, /*11*/6,
            /*12*/2, /*13*/7, /*14*/8, /*15*/7, /*16*/6, /*17*/5,
            /*18*/7, /*19*/7, /*20*/8, /*21*/8, /*22*/9
    };

    private static double[] PCriticoEnemigoN3 = {
            /*0*/0.20, /*1*/0.20, /*2*/0.20, /*3*/0.20, /*4*/0.22, /*5*/0.21,
            /*6*/0.18, /*7*/0.18, /*8*/0.20, /*9*/0.21, /*10*/0.22, /*11*/0.23,
            /*12*/0.25, /*13*/0.28, /*14*/0.30, /*15*/0.28, /*16*/0.26, /*17*/0.25,
            /*18*/0.28, /*19*/0.30, /*20*/0.32, /*21*/0.34, /*22*/0.36
    };


    private static boolean[] PMagicoEnemigoN3 = {
            /*0*/true,/*1*/false,/*2*/true,/*3*/true,/*4*/false,/*5*/false,
            /*6*/false,/*7*/false,/*8*/false,/*9*/false,/*10*/false,/*11*/false,};
    private static int[] DañoMagicoEnemigoN3 = {
            /*0*/5, /*1*/0, /*2*/10, /*3*/5, /*4*/0, /*5*/0,
            /*6*/0, /*7*/0, /*8*/0, /*9*/0, /*10*/0, /*11*/0};
    //---------------------------------------------------------------------------------------

    private static String[] ImgEnemigoN4 =
            {/*0*/"enemigodragonrojo.txt", /*1*/"in.txt", /*2*/"in.txt",/*3*/"in.txt", /*4*/"in.txt", /*5*/"in.txt"};
    private static String[] NombreEnemigoN4 = {
            /*0*/"Jefe Dragón Rojo", /*1*/"Reina de la Hidra", /*2*/"Demonio Supremo", /*3*/"Lord de la Lava", /*4*/"Ángel Caído", /*5*/"Tigre de Fuego"};

    private static int[] MinSaludEnemigoN4 = {
            /*0*/100, /*1*/110, /*2*/120, /*3*/130, /*4*/140};

    private static int[] ExtraSaludEnemigoN4 = {
            /*0*/20, /*1*/25, /*2*/30, /*3*/35, /*4*/40};

    private static int[] MinDañoEnemigoN4 = {
            /*0*/20, /*1*/22, /*2*/24, /*3*/26, /*4*/28};

    private static int[] ExtraDañoEnemigoN4 = {
            /*0*/10, /*1*/12, /*2*/14, /*3*/16, /*4*/18};

    private static int[] MaxDañoEnemigoN4 = {
            /*0*/30, /*1*/32, /*2*/34, /*3*/36, /*4*/38};

    private static int[] AgilidadEnemigoN4 = {
            /*0*/10, /*1*/11, /*2*/12, /*3*/13, /*4*/14};

    private static double[] PCriticoEnemigoN4 = {
            /*0*/0.30, /*1*/0.32, /*2*/0.34, /*3*/0.36, /*4*/0.38};

    private static boolean[] PMagicoEnemigoN4 = {
            /*0*/true,/*1*/false,/*2*/false,/*3*/false,/*4*/false,/*5*/false};
    private static int[] DañoMagicoEnemigoN4 = {
            /*0*/5, /*1*/0, /*2*/0, /*3*/0, /*4*/0, /*5*/0};
    //--------------------------------------------------------------------------------------------------------------------------

    private static int EnemigoIndiceSelec = 0;
    private static int EnemigoNivelSelec = 0;
    private static TipoSeleccion EnemigoCategoriaSelec;
    private static GrupoE EnemigoGrupoSelec;
    private static int IndiceEnemigo = 0;
    private static String HabilidadActualEnemigo = "";

    private static String EnemigoActualNombre;
    private static int SaludEnemigo = 0;
    private static int DañoMagico = 0;
    private static int DañoEnemigo = 0;  private static int DañoEnemigoPocion = 0;
    private static int DañoRestanteInvocaciónCaida = 0; //para reduccion del daño del gerrero
    private static int AgilidadEnemigo = 0;private static int AgilidadEnemigoPocion = 0;

    private  static int DañoRestanteEscudoMagico = 0;
    private  static int DañoRestanteEscudoFisico = 0;
    private  static int DañoRestanteArmadura = 0;



    private static int[] ContadorAgilidad = new int[4];
    private static int[] ContadorFuerza = new int[3];
    private static int[] ContadorResistencia = new int[3];
    private static int[] ContadorVelocidad = new int[3];
    private static int[] ContadorVeneno = new int[3];
    private static int[] ContadorFuego = new int[2];
    private static int[] ContadorDestreza = new int[3];


    private static int ContadorTurnos = 1; //Se inicializa con 1 por que si no empiza en 0
    private static int ContadorOportuno = 0;
    private static int ContadorConcentración = 0; //recarga de varita x2


    private static int ContadorVarita = 0;
    private static int ContadorAgilidadBaston = 0;
    private static int ContadorFuerzaBaston = 0;
    private static int ContadorVHielo = 0;
    private static int ContadorVDebilidad = 0;
    private static int ContadorVProtección = 0;


    private static int ContRecargaFuego = 0; public static int ContadorFuegoVarita = 0; public static int FuegoVarita = 0;
    private static int ContRecargaHielo = 0;
    private static int ContRecargaDebilidad = 0;
    private static int ContRecargaCuracion = 0;
    private static int ContRecargaBaston = 0;
    private static int ContRecargaProteccion = 0;


    private static int ContRecargaFuego2 = 0;
    private static int ContRecargaHielo2 = 0;
    private static int ContRecargaDebilidad2 = 0;
    private static int ContRecargaCuracion2 = 0;
    private static int ContRecargaBaston2 = 0;
    private static int ContRecargaProteccion2 = 0;

    private static int ContadorEscudoMagicoGuerrero = 0;
    private static int HabilidadEscudoMagicoGuerrero = 0;
    private static double ReduccionGuerreroDañoObtenido = 0.0;

    private static int ContadorParaInvocar = 2;



    private static boolean ComerValla = false;
    private static boolean ValleDuendeMusica = false;
    private static boolean Acertijo = false;



    private static boolean EncantamientoArmaduraEspinas = false;
    private static int DañoArmadura = 0;
    private static int PuntosRecargaArmadura = 0;
    private static boolean EncantamientoArmaduraReparación = false;
    private static boolean EncantamientoArmaduraAgilidad = false;
    private static boolean EncantamientoArmaduraRecarga = false;
    private static boolean EncantamientoEscudoEspinas = false;
    private static int DañoEscudo = 0;
    private static boolean EncantamientoEscudoBloqueo = false;
    private static boolean BloqueoEncantamiento = false;
    private static boolean EncantamientoEscudoResistencia = false;
    private static boolean ResistenciaEscudo = false;
    private static boolean EncantamientoEscudoParalisis = false;
    private static boolean ParalisisPorEscudo= false;
    private static boolean EncantamientoEspadaCritico = false;
    private static boolean CriticoEspada = false;
    private static boolean EncantamientoEspadaCurativo = false;
    private static boolean EncantamientoEspadaResistencia = false;
    private static int PuntosResistenciaEspada = 0;
    private static boolean EncantamientoEspadaVelocidad = false;
    private static boolean VelocidadEspada = false;





    private static int ContadorReparaciónArmadura = 0;
    private static int ContadorReparaciónEscudo = 0;
    private static int ContadorReparaciónEspada = 0;
    private static boolean EnCombateJugador = false;

    private static boolean SeleccionarClaseCorrecto = false;
    private static double Descuento = 0.0; //Esta programado al reves que con la reducción de daño del guerrero

    private static int longitudPatron = 5; // Longitud del patrón
    private static int milisegundosRetraso = 600; // Retraso de 1 segundo


    private static void inicializarMatriz() {
        Gemas = 25;

        Objetos[0] = "Poción de curación";
        Objetos[1] = "Poción de fuerza";
        Objetos[2] = "Poción de agilidad";
        Objetos[3] = "Mapa";
        Objetos[4] = "Antorcha";
        Objetos[5] = "Cristal de mana";
        Objetos[6] = "Bayas";
        Objetos[7] = "Poción de resistencia";
        Objetos[8] = "Poción de velocidad";
        Objetos[9] = "Poción de destreza";
        Objetos[10] = "Poción de concentración";
        Objetos[11] = "Frasco de tormenta";
        Objetos[12] = "Frasco de veneno";
        Objetos[13] = "Stilus arcano"; //armadura
        Objetos[14] = "Runa de los antiguos";//escudo
        Objetos[15] = "Pergamino del arcanum";//espada
        Objetos[16] = "Kit de reparación";
        Objetos[17] = "Pergamino de invocación";
        Objetos[18] = "Poción de curación grande";

        Cantidades[0] = 0; // Pociones de curación
        Cantidades[1] = 0; // Pociones de fuerza
        Cantidades[2] = 0; // Pociones de agilidad
        Cantidades[3] = 0; // Mapa
        Cantidades[4] = 1; // Antorcha
        Cantidades[5] = 0; // cristales
        Cantidades[6] = 0; // Bayas
        Cantidades[7] = 0; // Poción de resistencia
        Cantidades[8] = 0; // Poción de velocidad
        Cantidades[9] = 0; // Poción de destreza
        Cantidades[10] = 0;// Poción de concentración
        Cantidades[11] = 0;// Frasco de tormenta
        Cantidades[12] = 0;// Frasco de veneno
        Cantidades[13] = 0;// Stilus arcano
        Cantidades[14] = 0;// Runa de los antiguos
        Cantidades[15] = 0;// Pergamino del arcanum
        Cantidades[16] = 0;// Kit de reparación
        Cantidades[17] = 0;// Pergamino de invocación
        Cantidades[18] = 0;// Poción de curación grande


        Equipamiento[0] = "Jugador";//no se usa en nada
        Equipamiento[1] = "Armadura";
        Equipamiento[2] = "Escudo";
        Equipamiento[3] = "Espada";
        Equipamiento[4] = "Escudo mágico";

        SaludMax[0] = 0; //se define por la clase


        Salud[0] = 0; // Salud inicial del jugador
        Salud[1] = 0; // Salud inicial de la armadura.
        Salud[2] = 0; // Salud inicial del escudo
        Salud[3] = 0; // Salud o durabilidad de la espada.
        Salud[4] = 0; // Salud o durabilidad de escudo magico.

        Daño[0] = 0; //Daño base de jugador
        Daño[1] = 0; //Daño base de espada (Se agregará al tener espada)
        Daño[2] = 0; //Reservado para la poción de fuerza
        Daño[3] = 0; //Reservado para la poción de fuerza en invocación


        Agilidad[0] = 0;// Agilidad del jugador
        Agilidad[1] = 0; //Reservado para la poción de Agilidad.
        Agilidad[2] = 0;//Para varita de protección
        Agilidad[3] = 0; //Para varita de hielo
        Agilidad[4] = 0; //Para antorcha
        Agilidad[5] = 0; //Para encantamiento de armadura
        Agilidad[6] = 0; //Reservado para la poción de Agilidad en invocación


    }
    private static void MatrizVaritas() {
        NombreVarita[0] = "Bastón del mago";
        AgilidadBaston = 0;
        FuerzaBaston = 0;
        DañoVarita[0] = 0; //No se modifica
        EnergiaVarita[0] = 10;
        MaxEnergiaVarita[0] = 10;
        VaritaActiva[0] = false;

        NombreVarita[1] = "Varita de fuego";
        DañoVarita[1] = 4; //Daño progresivo al enemigo
        EnergiaVarita[1] = 4;
        MaxEnergiaVarita[1] = 4;
        VaritaActiva[1] = false;

        NombreVarita[2] = "Varita de hielo";
        DañoVarita[2] = 0; //No posee daño, se usa agilidad[3]
        EnergiaVarita[2] = 4;
        MaxEnergiaVarita[2] = 4;
        VaritaActiva[2] = false;

        NombreVarita[3] = "Varita de debilidad";
        DañoVarita[3] = 0;
        EnergiaVarita[3] = 4;
        MaxEnergiaVarita[3] = 4;
        VaritaActiva[3] = false;

        NombreVarita[4] = "Varita de curación";
        DañoVarita[4] = 15; //Lo uso como curación en ves de daño. (No se modifica)
        EnergiaVarita[4] = 2;
        MaxEnergiaVarita[4] = 2;
        VaritaActiva[4] = false;

        NombreVarita[5] = "Varita de protección";
        DañoVarita[5] = 0; //No posee daño, se usa agilidad[2]
        EnergiaVarita[5] = 4;
        MaxEnergiaVarita[5] = 4;
        VaritaActiva[5] = false;


    }
    private static void inicializarContadores() {
        ContadorAgilidad[0] = 0;//Jugador poción
        ContadorAgilidad[1] = 0;//Invocación poción
        ContadorAgilidad[2] = 0;//Enemigo(aun no esta)
        ContadorAgilidad[3] = 0;//Antorcha

        ContadorFuerza[0] = 0;//Jugador poción
        ContadorFuerza[1] = 0;//Invocación poción
        ContadorFuerza[2] = 0;//Enemigo(aun no esta)

        ContadorResistencia[0] = 0;//Jugador poción
        ContadorResistencia[1] = 0;//Invocación poción
        ContadorResistencia[2] = 0;//Enemigo(aun no esta)

        ContadorVelocidad[0] = 0;//Jugador poción
        ContadorVelocidad[1] = 0;//Invocación poción
        ContadorVelocidad[2] = 0;//Enemigo(aun no esta)

        ContadorVeneno[0] = 0;//Jugador poción
        ContadorVeneno[1] = 0;//Invocación poción
        ContadorVeneno[2] = 0;//Enemigo(aun no esta)

        ContadorDestreza[0] = 0;//Jugador poción
        ContadorDestreza[1] = 0;//Invocación poción
        ContadorDestreza[2] = 0;//Enemigo(aun no esta)

    }
    private static void mostrarVaritas() {
        System.out.println("");
        System.out.println(MAGENTA + "  Varitas disponibles:" + VERDE);
        for (int i = 0; i < varitasPoseidas.size(); i++) {
            int index = varitasPoseidas.get(i);

            if (NombreVarita[index] == NombreVarita[0]) {
                System.out.println(VERDE + "  " + (i + 1) + ". " + NombreVarita[index] + RESET + ". Energía actual: " + EnergiaVarita[0] + ", Max: " + MaxEnergiaVarita[0]);
            }
            if (NombreVarita[index] == NombreVarita[1]) {
                System.out.println(VERDE + "  " + (i + 1) + ". " + NombreVarita[index] + RESET + ". Energía actual: " + EnergiaVarita[1] + ", Max: " + MaxEnergiaVarita[1]);
            }
            if (NombreVarita[index] == NombreVarita[2]) {
                System.out.println(VERDE + "  " + (i + 1) + ". " + NombreVarita[index] + RESET + ". Energía actual: " + EnergiaVarita[2] + ", Max: " + MaxEnergiaVarita[2]);
            }
            if (NombreVarita[index] == NombreVarita[3]) {
                System.out.println(VERDE + "  " + (i + 1) + ". " + NombreVarita[index] + RESET + ". Energía actual: " + EnergiaVarita[3] + ", Max: " + MaxEnergiaVarita[3]);
            }
            if (NombreVarita[index] == NombreVarita[4]) {
                System.out.println(VERDE + "  " + (i + 1) + ". " + NombreVarita[index] + RESET + ". Energía actual: " + EnergiaVarita[4] + ", Max: " + MaxEnergiaVarita[4]);
            }
            if (NombreVarita[index] == NombreVarita[5]) {
                System.out.println(VERDE + "  " + (i + 1) + ". " + NombreVarita[index] + RESET + ". Energía actual: " + EnergiaVarita[5] + ", Max: " + MaxEnergiaVarita[5]);
            }
        }
    }
    private static void EncontrarVaritaAleatoria() {
        ArrayList<String> varitasDisponibles = new ArrayList<>();
        ArrayList<Integer> indicesDisponibles = new ArrayList<>();

        // Añadir varitas no poseídas a la lista
        for (int i = 1; i < NombreVarita.length; i++) {
            if (!VaritaActiva[i]) {
                varitasDisponibles.add(NombreVarita[i]);
                indicesDisponibles.add(i);
            }
        }

        // Si hay varitas disponibles, seleccionar una aleatoria
        if (!varitasDisponibles.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(varitasDisponibles.size());

            // Asignar la varita seleccionada al jugador
            String varitaSeleccionada = varitasDisponibles.get(index);
            int indiceVarita = indicesDisponibles.get(index);
            VaritaActiva[indiceVarita] = true;

            // Informar al jugador
            imprimirPocoAPoco(MAGENTA + "  Has Encontrado la " + varitaSeleccionada + "." + RESET, 20);
        } else {
            imprimirPocoAPoco("  Ya posees todas las varitas disponibles.", 20);
            imprimirPocoAPoco("  Tener otra igual no te sirve de nada y la dejas donde estaba.", 20);
        }
    }

    private static void AgregarNombre() {
        boolean nombreValido = false;
        Scanner scanner = new Scanner(System.in);

        List<String> palabrasProhibidas = Arrays.asList(
                "puto", "pito", "semen", "putoelquelea", "puoelquelea", "cago", "caga",
                "benito", "camelo", "pene", "mierda", "sexo", "culo", "culito", "melo", "chupa", "perra",
                "roberto", "tragatelo", "coño", "verga", "puta", "maricón", "maricon", "fuck", "masturbar",
                "shit", "asshole", "bitch", "cunt", "dick", "cock", "pussy", "bastard", "motherfucker", "ggg", "qqq", "sabrosona", "sabrosa",
                "culero", "culera", "maldita", "mamada", "gay", "gy", "tonto", "bastardo", "coger", "idiota",
                "maldito", "maldita", "estupido", "estupida", "polla", "tocamelo", "ticamela", "verga", "xxx", "xxz", "leche", "porno", "sexo",
                "gey", "droga", "marigu", "madre", "pendejo", "pendeja", "pta","pto", "put", "gai", "pitudo", "sexi", "sexy", "pela", "sexy", "jalame",
                "coge", "prr", "vergo", "mama", "pit0", "dr0ga", "put0", "6ei", "6e1", "sex0", "6ai", "6ay","64y", "porn0", "p0rn0", "pndejo", "pndeja", "pn",
                "i?","ene", "pndejo", "pndj", "pn", "pt"
        );

        while (!nombreValido) {
            System.out.println("");
            imprimirPocoAPoco("  Por favor ingresa un nombre de usuario (sin tildes).", 20);
            String nombreIngresado = scanner.nextLine();
            String nombrecopia = nombreIngresado; // Mantener el nombre original

            // Normaliza el nombre solo para la verificación
            String nombreNormalizado = nombreIngresado.toLowerCase()
                    .replaceAll("0", "o")
                    .replaceAll("1", "i")
                    .replaceAll("2", "r")
                    .replaceAll("3", "e")
                    .replaceAll("4", "a")
                    .replaceAll("5", "s")
                    .replaceAll("6", "g")
                    .replaceAll("7", "t")
                    .replaceAll("8", "b")
                    .replaceAll("9", "q")
                    .replaceAll("!", "i")
                    .replaceAll("¡", "i")
                    .replaceAll("¿", "d")
                    .replaceAll("ñ", "n")
                    .replaceAll("Ñ", "n")
                    .replaceAll("á", "a")
                    .replaceAll("é", "e")
                    .replaceAll("í", "i")
                    .replaceAll("ó", "o")
                    .replaceAll("ú", "u")
                    .replaceAll("Á", "a")
                    .replaceAll("É", "e")
                    .replaceAll("Í", "i")
                    .replaceAll("Ó", "o")
                    .replaceAll("Ú", "u")
                    .replaceAll("\\|", "i") // Reemplaza "|" con "i"
                    .replaceAll("[^a-z0-9]", "") // Elimina caracteres especiales y espacios
                    .replaceAll("(.)\\1+", "$1"); // Elimina caracteres repetidos consecutivos

            System.out.println("  Validando: " + nombreNormalizado);
            imprimirPocoAPoco(" . . .", 400);

            // Verifica si contiene palabras prohibidas
            boolean contienePalabraProhibida = false;
            for (String palabra : palabrasProhibidas) {
                if (nombreNormalizado.contains(palabra)) {
                    contienePalabraProhibida = true;
                    break;
                }
            }

            if (contienePalabraProhibida) {
                imprimirPocoAPoco(ROJO + "  Nombre no disponible, se detectó palabras inadecuadas.", 15);
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();
                borrarConsola(10);
            } else {
                nombreValido = true;
                NombreJugador = nombrecopia // Mantiene el nombre original con tildes
                        .replaceAll("[^a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ]", ""); // Solo elimina caracteres no deseados
                System.out.println("");
                imprimirPocoAPoco(VERDE + "  Nombre aceptado: " + NombreJugador + RESET, 20);

                if (nombreNormalizado.equalsIgnoreCase("modorot")) {
                    modoroot = true;
                    System.out.println("");
                    imprimirPocoAPoco(MAGENTA + "  Modo de desarrollador activado." + RESET, 20);
                }
            }
        }

    }


    private static void SeleccionarClase() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        imprimirPocoAPoco("  Por favor, seleccione una clase para poder iniciar.", 20);
        imprimirPocoAPoco(VERDE + "  1. Clase guerrero.", 20);
        imprimirPocoAPoco("  2. Clase Mago.", 20);
        imprimirPocoAPoco("  3. Clase invocador." + RESET, 20);
        imprimirPocoAPoco(MAGENTA + "  4. Clase aleatoria." + RESET, 20);

        imprimirPocoAPoco(AZUL + "  Escoge una de las opciones anteriores." + RESET, 20);
        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            System.out.println("");
            if (opcion == 1) {
                ClaseGuerrero();
                System.out.println("");
                mostrarimagen("guerrero.txt");
                SeleccionarClaseCorrecto = true;
            } else if (opcion == 2) {
                ClaseMago();
                System.out.println("");
                mostrarimagen("mago.txt");
                SeleccionarClaseCorrecto = true;
            } else if (opcion == 3) {
                ClaseInvocador();
                System.out.println("");
                mostrarimagen("invocador.txt");
                SeleccionarClaseCorrecto = true;
            } else {
                int ClaseRandom = (int) (Math.random() * 3) + 1;
                if (ClaseRandom == 1) {
                    ClaseGuerrero();
                    System.out.println("");
                    mostrarimagen("guerrero.txt");
                    SeleccionarClaseCorrecto = true;
                } else if (ClaseRandom == 2) {
                    ClaseMago();
                    System.out.println("");
                    mostrarimagen("mago.txt");
                    SeleccionarClaseCorrecto = true;
                } else if (ClaseRandom == 3) {
                    ClaseInvocador();
                    System.out.println("");
                    mostrarimagen("invocador.txt");
                    SeleccionarClaseCorrecto = true;
                }
            }
        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);
            SeleccionarClase();
        }


    }
    private static void ClaseGuerrero() {
        SaludMax[0] = AtributosSaludClase[0];
        Daño[0] = AtributosDañoClase[0];
        Agilidad[0] = AtributosAgilidadClase[0];
        PorcentajeReduccionDañoEspada = AtributosReduccionDañoClase[0];
        ProbabilidadCriticoJugador = AtributosCriticoClase[0];

        Salud[1] = 5; // Salud inicial de la armadura.
        Cantidades[1] += 1;
        Cantidades[7] += 1; // Poción de resistencia
        Cantidades[8] += 1; // Poción de velocidad
        ReduccionGuerreroDañoObtenido = 0.8; //Esto indica el daño que se quedara, en este caso solo el 20% del daño se vera negada
        HabilidadEscudoMagicoGuerrero = 5; // Obtiene esta cantidad de armadura al no tener armaduro por 5 turnos seguidos.

        Salud[0] += SaludMax[0];
        ClaseGuerrero = true;
        imprimirPocoAPoco(MAGENTA + "  Clase guerrero seleccionado" + RESET, 20);
        System.out.println("");

        if (modoroot) {
            MODOROOT();
        }
    }
    private static void ClaseMago() {
        SaludMax[0] = AtributosSaludClase[1];
        Daño[0] = AtributosDañoClase[1];
        Agilidad[0] = AtributosAgilidadClase[1];
        PorcentajeReduccionDañoEspada = AtributosReduccionDañoClase[1];
        ProbabilidadCriticoJugador = AtributosCriticoClase[1];

        VaritaActiva[0] = true;
        NivelBaston_1 = true;
        Cantidades[5] += 1;
        Cantidades[12] += 1;// Frasco de veneno
        ClaseMago = true;
        Salud[0] += SaludMax[0];

        imprimirPocoAPoco(MAGENTA + "  Clase mago seleccionado" + RESET, 20);
        System.out.println("");

        if (modoroot) {
            MODOROOT();
        }
    }
    private static void ClaseInvocador() {
        SaludMax[0] = AtributosSaludClase[2];
        Daño[0] = AtributosDañoClase[2];
        Agilidad[0] = AtributosAgilidadClase[2];
        PorcentajeReduccionDañoEspada = AtributosReduccionDañoClase[2];
        ProbabilidadCriticoJugador = AtributosCriticoClase[2];

        Cantidades[1] += 1;
        Cantidades[11] += 1;// Frasco de tormenta

        ClaseInvocador = true;
        Salud[0] = Salud[0] + SaludMax[0];
        imprimirPocoAPoco(MAGENTA + "  Clase Invocador seleccionado" + RESET, 20);
        System.out.println("");

        monstruosInvocados.add(0); // Fénix
        monstruosInvocados.add(1); // Gólem de Piedra
        monstruosInvocados.add(2); // Espíritu del Bosque


        if (modoroot) {
            MODOROOT();
        }
    }
    private static void MODOROOT() {
        Salud[1] = 100; // Salud inicial de la armadura.
        Salud[2] = 50; // Salud inicial del escudo
        Salud[3] = 50; // Salud o durabilidad de la espada.
        Salud[4] = 40; // Salud o durabilidad de escudo magico.
        Daño[0] = 10; //Daño base de jugador
        Daño[1] = 7; //Daño base de espada (Se agregará al tener espada)
        Agilidad[0] = 4;// Agilidad del jugador
        SaludMax[0] = 1000;
        Salud[0] = SaludMax[0];

        VaritaActiva[1] = true;
        VaritaActiva[2] = true;
        VaritaActiva[3] = true;
        VaritaActiva[4] = true;
        VaritaActiva[5] = true;

        Cantidades[0] = 20; // Pociones de curación
        Cantidades[1] = 20; // Pociones de fuerza
        Cantidades[2] = 20; // Pociones de agilidad
        Cantidades[3] = 0; // Mapa
        Cantidades[4] = 20; // Antorcha
        Cantidades[5] = 15; // cristales
        Cantidades[6] = 30; // Bayas
        Cantidades[7] = 20; // Poción de resistencia
        Cantidades[8] = 20; // Poción de velocidad
        Cantidades[9] = 20; // Poción de destreza
        Cantidades[10] = 20;// Poción de concentración
        Cantidades[11] = 20;// Frasco de tormenta
        Cantidades[12] = 20;// Frasco de veneno
        Cantidades[13] = 10;// Stilus arcano
        Cantidades[14] = 10;// Runa de los antiguos
        Cantidades[15] = 10;// Pergamino del arcanum
        Cantidades[16] = 10;// Kit de reparación
        Cantidades[17] = 10;// Pergamino de invocación

        Gemas = 10000;

        monstruosInvocados.add(3); // Dragón de Hielo
        monstruosInvocados.add(4); // Guardián de Hierro
        monstruosInvocados.add(5); // Ángel Guardián
        monstruosInvocados.add(6); // Elfo Sanador
        monstruosInvocados.add(7); // Vampiro Ancestral
        monstruosInvocados.add(8); // Espectro Sombrío
        monstruosInvocados.add(9); // Nigromante Oscuro
    }


    private static void comerciante() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarComprando = true;

        while (continuarComprando) {
            List<String> itemsDisponibles = new ArrayList<>();
            for (String objeto : Objetos) {
                if (objeto != null && !objeto.equals("Mapa") && !objeto.equals("Poción de curación grande") ) {
                    itemsDisponibles.add(objeto);
                }
            }
            for (String equipo : Equipamiento) {
                if (equipo != null && !equipo.equals("Jugador") && !equipo.equals("Escudo mágico")) {
                    itemsDisponibles.add(equipo);
                }
            }
            for (int i = 1; i < NombreVarita.length; i++) {
                if (!VaritaActiva[i]) {
                    itemsDisponibles.add(NombreVarita[i]);
                }
            }

            Collections.shuffle(itemsDisponibles);
            System.out.println("");
            System.out.println(CYAN + "  Tus gemas: " + Gemas);
            imprimirPocoAPoco(AMARILLO + "  El comerciante tiene los siguientes objetos a la venta:" + RESET, 20);
            int[] precios = new int[6];

            for (int i = 0; i < 6 && i < itemsDisponibles.size(); i++) {
                String item = itemsDisponibles.get(i);
                if (item != null) {  // Verifica que el item no sea null
                    int precio = generarPrecioAleatorio(item);
                    precios[i] = precio;

                    if (Descuento > 0.0) {
                        double precioConDescuento = precio * (1 - Descuento);
                        imprimirPocoAPoco(VERDE + "  " + (i + 1) + ". " + item + RESET + " - Precio original: " + precio + " gemas " + CYAN + "- Descuento: " + (int) (Descuento * 100) + "%" + RESET + " - Precio final: " + (int) precioConDescuento + " gemas", 5);
                    } else {
                        imprimirPocoAPoco(VERDE + "  " + (i + 1) + ". " + item + RESET + " - Precio: " + precio + " gemas", 10);
                    }
                }
            }

            imprimirPocoAPoco(AZUL + "  Elige un objeto para comprar (0 para salir): " + RESET, 20);

            if (scanner.hasNextInt()) {
                int eleccion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea

                System.out.println("");
                if (eleccion > 0 && eleccion <= 6 && eleccion <= itemsDisponibles.size()) {
                    String itemComprado = itemsDisponibles.get(eleccion - 1);
                    int precio = precios[eleccion - 1];

                    int precioConDescuento = precio;
                    if (Descuento > 0.0) {
                        //precio *= (1 - Descuento);
                        precioConDescuento = (int) (precio * (1 - Descuento));
                    }

                    if (Gemas >= precioConDescuento) {
                        Gemas -= precioConDescuento;
                        imprimirPocoAPoco(MAGENTA + "  Has comprado " + itemComprado + " por " + precioConDescuento + " gemas." + RESET, 20);
                        procesarCompra(itemComprado, VaritaActiva, Salud, Cantidades, precio);
                    } else {
                        imprimirPocoAPoco(ROJO + "  No tienes suficientes gemas para comprar " + itemComprado + "." + RESET, 30);
                    }
                } else {
                    continuarComprando = false;
                }
            } else {
                scanner.nextLine();  // Consumir la entrada no numérica
                imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
                System.out.println("  Cargando...");
                imprimirPocoAPoco(" . . .", 400);
                comerciante();
            }
        }
        // Resetear el descuento al finalizar la compra
        Descuento = 0.0;
    }
    private static int generarPrecioAleatorio(String item) {
        Random random = new Random();

        // Mapa con el rango de precios (min, max) para cada objeto
        Map<String, int[]> rangoPreciosMap = new HashMap<>();
        rangoPreciosMap.put("Poción de curación", new int[]{10, 25});
        rangoPreciosMap.put("Poción de fuerza", new int[]{10, 25});
        rangoPreciosMap.put("Poción de agilidad", new int[]{5, 20});
        rangoPreciosMap.put("Antorcha", new int[]{2, 5});
        rangoPreciosMap.put("Cristal de mana", new int[]{10, 20});
        rangoPreciosMap.put("Armadura", new int[]{10, 55});
        rangoPreciosMap.put("Escudo", new int[]{15, 55});
        rangoPreciosMap.put("Espada", new int[]{10, 55});
        rangoPreciosMap.put("Bayas", new int[]{5, 15});
        rangoPreciosMap.put("Poción de resistencia", new int[]{10, 30});
        rangoPreciosMap.put("Poción de velocidad", new int[]{10, 35});
        rangoPreciosMap.put("Poción de destreza", new int[]{5, 20});
        rangoPreciosMap.put("Poción de concentración", new int[]{5, 15});
        rangoPreciosMap.put("Frasco de tormenta", new int[]{15, 35});
        rangoPreciosMap.put("Frasco de veneno", new int[]{10, 25});
        rangoPreciosMap.put("Stilus arcano", new int[]{5, 10});
        rangoPreciosMap.put("Runa de los antiguos", new int[]{5, 10});
        rangoPreciosMap.put("Pergamino del arcanum", new int[]{5, 10});
        rangoPreciosMap.put("Kit de reparación", new int[]{10, 25});
        rangoPreciosMap.put("Pergamino de invocación", new int[]{35, 60});


        // Obtener el rango de precios para el objeto
        int[] rango = rangoPreciosMap.getOrDefault(item, new int[]{35, 65});

        // Generar un precio aleatorio dentro del rango
        int precioAleatorio = rango[0] + random.nextInt(rango[1] - rango[0] + 1);
        return precioAleatorio;
    }
    private static void procesarCompra(String item, boolean[] VaritaActiva, int[] Salud, int[] Cantidades, int precio) {
        Random random = new Random();

        if (item.equals("Armadura")) {
            // para no confundirme de nuevo
            //el primer valor "20" es la salud base a aplicar(colocada por mi), si el precio pagado es mayor al precio minimo "10"(Verificar en los precios que este igual)
            //entonces si es mayor, este se multiplicara con el valor puesto por mi que es 1.5, e resultado se sumara con la salud base "20"
            // y a demas se le aplicara una bonificación aleatoria de hasta maximo de 9 de salud extra a armadura.
            Salud[1] = (int) (20 + (precio - 10) * 1.5 + random.nextInt(10));
            imprimirPocoAPoco("  Salud de armadura: " + Salud[1], 20);
            System.out.println("");
            ContadorReparaciónArmadura = 0;
            EncantamientoArmaduraEspinas = false;
            DañoArmadura = 0;
            PuntosRecargaArmadura = 0;
            EncantamientoArmaduraReparación = false;
            EncantamientoArmaduraAgilidad = false;
            EncantamientoArmaduraRecarga = false;

        } else if (item.equals("Escudo")) {
            Salud[2] = (int) (7 + (precio - 15) * 1.5 + random.nextInt(10));
            imprimirPocoAPoco("  Salud de escudo: " + Salud[2], 20);
            System.out.println("");
            ContadorReparaciónEscudo = 0;
            EncantamientoEscudoEspinas = false;
            DañoEscudo = 0;
            EncantamientoEscudoBloqueo = false;
            BloqueoEncantamiento = false;
            EncantamientoEscudoResistencia = false;
            ResistenciaEscudo = false;
            EncantamientoEscudoParalisis = false;
            ParalisisPorEscudo= false;

        } else if (item.equals("Espada")) {
            Salud[3] = (int) (5 + (precio - 9) * 0.4 + random.nextInt(3));
            Daño[1] = (int) (3 + (precio - 10) * 0.3 + random.nextInt(3));
            imprimirPocoAPoco("  Daño de espada: " + Daño[1], 20);
            imprimirPocoAPoco("  Salud de espada: " + Salud[3], 20);
            System.out.println("");
            ContadorReparaciónEspada = 0;
            EncantamientoEspadaCritico = false;
            CriticoEspada = false;
            EncantamientoEspadaCurativo = false;
            EncantamientoEspadaResistencia = false;
            PuntosResistenciaEspada = 0;
            EncantamientoEspadaVelocidad = false;
            VelocidadEspada = false;

        } else if (item.equals("Poción de curación")) {
            Cantidades[0]++;
            imprimirPocoAPoco("  Poción de curación obtenida: +1", 20);
            System.out.println("");
        } else if (item.equals("Poción de fuerza")) {
            Cantidades[1]++;
            imprimirPocoAPoco("  Poción de fuerza obtenida: +1", 20);
            System.out.println("");
        } else if (item.equals("Poción de agilidad")) {
            Cantidades[2]++;
            imprimirPocoAPoco("  Poción de agilidad obtenida: +1", 20);
            System.out.println("");
        } else if (item.equals("Antorcha")) {
            Cantidades[4]++;
            imprimirPocoAPoco("  Antorcha obtenida: +1", 20);
            System.out.println("");
        } else if (item.equals("Cristal de mana")) {
            Cantidades[5]++;
            imprimirPocoAPoco("  Cristal de mana obtenida: +1", 20);
            System.out.println("");
        } else if (item.equals("Bayas")) {
            int bayasobtenidas = (int) (1 + (precio - 4) * 0.4);
            Cantidades[6] += bayasobtenidas;
            imprimirPocoAPoco("  Bayas obtenidas: +" + bayasobtenidas, 20);
            System.out.println("");
        }else if (item.equals("Poción de resistencia")) {
            Cantidades[7]++;
            imprimirPocoAPoco("  Poción de resistencia obtenida: +1", 20);
            System.out.println("");
        }else if (item.equals("Poción de velocidad")) {
            Cantidades[8]++;
            imprimirPocoAPoco("  Poción de velocidad obtenida: +1", 20);
            System.out.println("");
        }else if (item.equals("Poción de destreza")) {
            Cantidades[9]++;
            imprimirPocoAPoco("  Poción de destreza obtenida: +1", 20);
            System.out.println("");
        }else if (item.equals("Poción de concentración")) {
            Cantidades[10]++;
            imprimirPocoAPoco("  Poción de concentración obtenida: +1", 20);
            System.out.println("");
        }else if (item.equals("Frasco de tormenta")) {
            Cantidades[11]++;
            imprimirPocoAPoco("  Frasco de tormenta obtenida: +1", 20);
            System.out.println("");
        }
        else if (item.equals("Frasco de veneno")) {
            Cantidades[12]++;
            imprimirPocoAPoco("  Frasco de veneno obtenida: +1", 20);
            System.out.println("");
        }else if (item.equals("Stilus arcano")) {
            Cantidades[13]++;
            imprimirPocoAPoco("  Stilus arcano obtenido: +1", 20);
            imprimirPocoAPoco(CYAN + "  * Sirve para encantar la armadura." +AMARILLO + " (Comprar armadura, remplaza la armadura actual y elimina los encantamientos.)"+  RESET, 5);
            System.out.println("");
        }
        else if (item.equals("Runa de los antiguos")) {
            Cantidades[14]++;
            imprimirPocoAPoco("   Runa de los antiguos obtenido: +1", 20);
            imprimirPocoAPoco(CYAN + "  * Sirve para encantar el escudo." + AMARILLO + " (Comprar escudo, remplaza el escudo actual y elimina los encantamientos.)"+ RESET, 5);
            System.out.println("");
        }
        else if (item.equals("Pergamino del arcanum")) {
            Cantidades[15]++;
            imprimirPocoAPoco("   Pergamino del arcanum obtenido: +1", 20);
            imprimirPocoAPoco(CYAN + "  * Sirve para encantar la espada." +AMARILLO + " (Comprar espada, remplaza la espada actual y elimina los encantamientos.)"+  RESET, 5);
            System.out.println("");
        }
        else if (item.equals("Kit de reparación")) {
            Cantidades[16]++;
            imprimirPocoAPoco("   Kit de reparación obtenido: +1", 20);
            imprimirPocoAPoco(AMARILLO + "  * Puede reparar hasta 2 veces un mismo equipamiento, hasta adquirir otro equipamiento del mismo tipo." + RESET, 5);
            System.out.println("");
        }
        else if (item.equals("Pergamino de invocación")) {
            Cantidades[17]++;
            imprimirPocoAPoco("   Pergamino de invocación obtenido: +1", 20);
            imprimirPocoAPoco(CYAN + "  * Invoca a aliado \"Guerrero eterno\" en combate." + RESET, 5);
            System.out.println("");
        }else {
            Map<String, Integer> varitaMap = new HashMap<>();
            varitaMap.put("Varita de fuego", 1);
            varitaMap.put("Varita de hielo", 2);
            varitaMap.put("Varita de debilidad", 3);
            varitaMap.put("Varita de curación", 4);
            varitaMap.put("Varita de protección", 5);

            if (varitaMap.containsKey(item)) {
                VaritaActiva[varitaMap.get(item)] = true;
            }
        }
    }

    private static void imprimirPocoAPoco(String mensaje, int milisegundos) {
        for (char c : mensaje.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(milisegundos);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error: " + e.getMessage());
            }
        }
        System.out.println(); // Nueva línea al final del mensaje
    }

    private Clip clip;
    private static Main audioPlayer = new Main();
    private boolean shouldRepeat = true;
    private void play(String audioFilePath) {
        try {
            stop(); // Detiene y cierra cualquier clip que esté en reproducción

            InputStream audioSrc = getClass().getClassLoader().getResourceAsStream(audioFilePath);
            if (audioSrc == null) {
                System.err.println("No se pudo encontrar el archivo de audio: " + audioFilePath);
                return;
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    if (shouldRepeat) {
                        clip.setFramePosition(0); // Vuelve al inicio del audio
                        clip.start(); // Reproduce de nuevo
                    } else {
                        clip.close(); // Cierra el clip cuando no deba repetirse
                    }
                }
            });

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }
    private void stop() {
        shouldRepeat = false; // Indica que no debe repetirse la canción actual
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // Detiene el clip si está en reproducción
            }
            clip.close(); // Cierra el clip para liberar recursos
            clip = null; // Asegúrate de que el clip se reinicie
        }
        shouldRepeat = true;
    }
    private void setRepeat(boolean repeat) {
        this.shouldRepeat = repeat;
    }

    private static void PatronMinijuego() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Acertijo = false;

        // Pedir al usuario que ingrese el patrón
        for (int intento = 0; intento < 2; intento++) {
            // Generar nuevo patrón aleatorio
            StringBuilder patron = new StringBuilder();
            for (int i = 0; i < longitudPatron; i++) {
                if (random.nextBoolean()) {
                    // Generar una letra minúscula
                    char letra = (char) (random.nextInt(26) + 'a');
                    patron.append(letra);
                } else {
                    // Generar un número
                    char numero = (char) (random.nextInt(10) + '0');
                    patron.append(numero);
                }
            }

            // Mostrar el patrón caracter por caracter con retraso
            for (char c : patron.toString().toCharArray()) {
                imprimirPocoAPoco(MAGENTA + "" + String.valueOf(c) + RESET, milisegundosRetraso);
                borrarConsola(1);
            }

            System.out.print("  Ingresa el patrón: ");
            String entradaUsuario = scanner.nextLine().trim();

            if (entradaUsuario.equals(patron.toString())) {
                Acertijo = true;
                break;
            } else {
                System.out.println(ROJO + "  Patrón incorrecto. Intenta de nuevo." + RESET);
            }
        }

    }
    private static void mostrarimagen(String nombreArchivo){
    // Obtener el archivo desde los recursos
    InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(nombreArchivo);

    if (inputStream == null) {
        System.err.println("No se pudo encontrar el archivo: " + nombreArchivo);
        return;
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            System.out.println(linea);
        }
    } catch (IOException e) {
        System.err.println("Error al leer el archivo: " + e.getMessage());
        e.printStackTrace();
    }



}
    private static void mostrarInventario() {
        calcularDañoTotalJugador();
        System.out.println("");
        imprimirPocoAPoco("-----------------------------------------------------------------------------------------", 2);
        if (modoroot) {
            imprimirPocoAPoco(MAGENTA + "  Atributos del desarrollador " + NombreJugador +  ", exp: "+Experiencia + "/"+ExperienciaMax + RESET, 5);
        } else if (ClaseGuerrero) {
            imprimirPocoAPoco(MAGENTA + "  Atributos del GUERRERO " + NombreJugador + ", nivel: "+ NivelJugador +  ", exp: "+Experiencia + "/"+ExperienciaMax + RESET, 5);
        } else if (ClaseMago) {
            imprimirPocoAPoco(MAGENTA + "  Atributos del MAGO " + NombreJugador + ", nivel: "+ NivelJugador + ", exp: "+Experiencia + "/"+ExperienciaMax +  RESET, 5);
        } else if (ClaseInvocador) {
            imprimirPocoAPoco(MAGENTA + "  Atributos del INVOCADOR " + NombreJugador + ", nivel: "+ NivelJugador +  ", exp: "+Experiencia + "/"+ExperienciaMax + RESET, 5);
        }
        imprimirPocoAPoco("  * Salud del jugador: " + Salud[0] + ", máx: " +  SaludMax[0], 5);
        imprimirPocoAPoco("  * Agilidad de jugador: " + Agilidad[0], 5);
        imprimirPocoAPoco("  * Daño base jugador: " + Daño[0], 5);
        imprimirPocoAPoco("  * Daño total jugador: " + DañoMinJugador + " - " + DañoMaxJugador + BLANCO + " - Base + Espada + Poción + Baston(Clase Mago)." +RESET, 5);
        imprimirPocoAPoco("  * Probabilidad de critico: " + ((int) (100 * ProbabilidadCriticoJugador)) + "%", 5);

        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Equipamiento: " + RESET, 5);
        for (int i = 1; i < Salud.length; i++) {
            if (Salud[i] > 0) {
                imprimirPocoAPoco("  * Durabilidad de " + Equipamiento[i] + ": " + Salud[i], 5);
            }

        }


        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Encantamientos activos: " + RESET, 5);
        if (ClaseGuerrero){
            imprimirPocoAPoco(MAGENTA + "  * Habilidad de guerrero, escudo mágico: +"+ HabilidadEscudoMagicoGuerrero + RESET + " , Turnos para activación: " + ContadorEscudoMagicoGuerrero + "/5", 5);
        }
        if (EncantamientoArmaduraEspinas){
            imprimirPocoAPoco(CYAN + "  * Armadura:"+ RESET + "  Encantamiento de espinas, daño a enemigo por daño a armadura: 30%", 5);
        }
        if (EncantamientoArmaduraReparación){
            imprimirPocoAPoco(CYAN + "  * Armadura:"+ RESET + "  Encantamiento de reparcación, reparación al daño recibido: 0% - 100%", 5);
        }
        if (EncantamientoArmaduraAgilidad){
            imprimirPocoAPoco(CYAN + "  * Armadura:"+ RESET + "  Encantamiento de agilidad, agilidad por encantamiento: " + Agilidad[5], 5);
        }
        if (EncantamientoArmaduraRecarga){
            imprimirPocoAPoco(CYAN + "  * Armadura:"+ RESET + "  Encantamiento de recarga, puntos de recarga: " + PuntosRecargaArmadura + "/2", 5);
        }
//------------------------------------------------------------------------------------------------------
        if (EncantamientoEscudoEspinas){
            imprimirPocoAPoco(AZUL + "  * Escudo:"+ RESET + "  Encantamiento de espinas, daño a enemigo por daño a escudo: 60%", 5);
        }
        if (EncantamientoEscudoBloqueo){
            imprimirPocoAPoco(AZUL + "  * Escudo:"+ RESET + "  Encantamiento de bloqueo, probabilidad de bloquar: 37%", 5);
        }
        if (EncantamientoEscudoResistencia){
            imprimirPocoAPoco(AZUL + "  * Escudo:"+ RESET + "  Encantamiento de resistencia, probabilidad de reducir daño: 46%, reducción: 30%", 5);
        }
        if (EncantamientoEscudoParalisis){
            imprimirPocoAPoco(AZUL + "  * Escudo:"+ RESET + "  Encantamiento de paralisis, probabilidad de paralisar enemigo: 33%", 5);
        }
//---------------------------------------------------------------------------------------------------------

        if (EncantamientoEspadaCritico){
            imprimirPocoAPoco(VERDE + "  * Espada:"+ RESET + " Encantamiento critico, activación: 60%, probabilidad de critico +20%, aumento de daño: +50% - +100%" + BLANCO + " Depende de factores del juego" + RESET, 5);
        }
        if (EncantamientoEspadaCurativo){
            imprimirPocoAPoco(VERDE + "  * Espada:"+ RESET + "  Encantamiento curación, probabilidad de curar: 50%, curación a jugador: 1 - " + Daño[1], 5);
        }
        if (EncantamientoEspadaResistencia){
            imprimirPocoAPoco(VERDE + "  * Espada:"+ RESET + "  Encantamiento de resistencia, puntos de resistencia: " + PuntosResistenciaEspada + "/4", 5);
        }
        if (EncantamientoEspadaVelocidad){
            imprimirPocoAPoco(VERDE + "  * Espada:"+ RESET + "  Encantamiento de velocidad, probabilidad de atacar de nuevo: 50%", 5);
        }



        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Potenciadores: " + RESET, 5);
        if (ClaseGuerrero) {
            double porcentajemostrar = ((ReduccionGuerreroDañoObtenido * 100) - 100);
            imprimirPocoAPoco(CYAN + "  Reducción de daño recibido por clase guerrero: " + (int) porcentajemostrar + "%" + RESET, 5);
        }
        if (EncantamientoArmaduraAgilidad){

            imprimirPocoAPoco("  * Agilidad por encantamiento armadura: " + Agilidad[5], 5);
        }
        if (Agilidad[1] > 0) {
            imprimirPocoAPoco("  * Agilidad de poción: " + Agilidad[1], 5);
        }
        if (Agilidad[4] > 0) {
            imprimirPocoAPoco("  * Agilidad por antorcha: " + Agilidad[1], 5);
        }
        if (AgilidadBaston > 0) {
            imprimirPocoAPoco("  * Agilidad por magia del baston: " + AgilidadBaston, 5);
        }
        if (Daño[1] > 0) {
            imprimirPocoAPoco("  * Daño de espada: " + Daño[1], 5);
            ReduccionDañoEspada = (int) (Daño[1] * PorcentajeReduccionDañoEspada);
            if (ClaseMago) {
                imprimirPocoAPoco(ROJO + "  -> Daño de espada reducido por clase mago: " + ReduccionDañoEspada + ", -" + (int)(100 * (1 - PorcentajeReduccionDañoEspada) ) + "%" + RESET, 5);
            }
            if (ClaseInvocador) {
                imprimirPocoAPoco(ROJO + "  -> Daño de espada reducido por clase invocador: " + ReduccionDañoEspada + ", -" +(int)(100 * (1 - PorcentajeReduccionDañoEspada) ) + "%" +RESET, 5);
            }
            if (ClaseGuerrero && PorcentajeReduccionDañoEspada < 1) {
                imprimirPocoAPoco(ROJO + "  -> Daño de espada reducido por clase Guerrero: " + ReduccionDañoEspada + ", -" +(int)(100 * (1 - PorcentajeReduccionDañoEspada)  ) + "%" + RESET, 5);
            } else if (ClaseGuerrero) {
                imprimirPocoAPoco(CYAN + "  -> Daño de espada aumentado por clase Guerrero: " + ReduccionDañoEspada + ", +" +(int)(100 * (1 - PorcentajeReduccionDañoEspada)  ) + "%" + RESET, 5);
            }
        } //Espada daño y reduccion
        if (Daño[2] > 0) {
            imprimirPocoAPoco("  * Daño de poción: " + Daño[2], 5);
        }
        if (FuerzaBaston > 0) {
            imprimirPocoAPoco("  * Daño por fuerza de bastón: " + FuerzaBaston, 5);
        }
        if (ContadorResistencia[0] > 0) {
            imprimirPocoAPoco("  * Reducción de daño por poción: 20%", 5);
        }
        if (ContadorVelocidad[0] > 0) {
            imprimirPocoAPoco("  * Velocidad de ataque: x2", 5);
        }
        if (ContadorDestreza[0] > 0) {
            imprimirPocoAPoco("  * Daño máximo por poción de destreza: " + DañoMaxJugador, 5);
        }
        if (ContadorConcentración > 0) {
            imprimirPocoAPoco("  * Recarga de varitas por poción de concentración: x2" + RESET, 5);
        }


        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Varitas: " + RESET, 5);
        for (int i = 0; i < NombreVarita.length; i++) {
            if (VaritaActiva[i] == true) {
                if (i == 0 && NivelBaston_1 == true){
                    imprimirPocoAPoco("  * " + NombreVarita[i] + " de nivel 1, Energia: " + EnergiaVarita[i] + "/" + MaxEnergiaVarita[i], 5);
                }else if (i == 0  && NivelBaston_2 == true){
                    imprimirPocoAPoco("  * " + NombreVarita[i] + " de nivel 2, Energia: " + EnergiaVarita[i] + "/" + MaxEnergiaVarita[i], 5);
                }else {
                    imprimirPocoAPoco("  * " + NombreVarita[i] + ", Energia: " + EnergiaVarita[i] + "/" + MaxEnergiaVarita[i], 5);
                }
            }

        }


        if (ClaseInvocador){
            System.out.println("");
            imprimirPocoAPoco(AMARILLO + "  Invocaciones disponibles:" + RESET, 5);
            for (int i = 0; i < monstruosInvocados.size(); i++) {
                int index = monstruosInvocados.get(i);
                imprimirPocoAPoco("  * " + NombreMonstruo[index] + RESET, 5);
            }
        }


        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Inventario: " + RESET, 5);
        for (int i = 0; i < Objetos.length; i++) {
            if (Cantidades[i] > 0) {
                imprimirPocoAPoco("  * " + Objetos[i] + ": " + Cantidades[i], 5);
            }

        }
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Recurso de pago: " + RESET, 5);
        imprimirPocoAPoco("  * Gemas: " + Gemas, 5);
        imprimirPocoAPoco("-----------------------------------------------------------------------------------------", 2);
        System.out.println("");
    }
    private static void borrarConsola(int numLineas) {
        // Secuencia de escape para borrar líneas específicas
        for (int i = 0; i < numLineas; i++) {
            System.out.print("\033[2K"); // Borra la línea actual
            System.out.print("\033[1A"); // Mueve el cursor a la línea anterior
        }

        System.out.flush();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  ¡Advertencia!, coloca el volumen al 50% o menos." + RESET, 7);
        imprimirPocoAPoco(VERDE + "  Coloca la ventana en pantalla completa para una mejor experiencia." + RESET, 7);
        inicializarMatriz();
        MatrizVaritas();
        inicializarMatrizEnemigos();
        DeciciónInicial(scanner);

    }
    private static void DeciciónInicial(Scanner scanner){
        System.out.println("");
        imprimirPocoAPoco(AZUL + "  ¿Qué te gustaria hacer?" + RESET, 7);
        imprimirPocoAPoco(VERDE + "  1. Ver guía de juego" + RESET, 7);
        imprimirPocoAPoco(VERDE + "  2. Jugar el modo historia." + RESET, 7);
        imprimirPocoAPoco(VERDE + "  3. Batalla infinita." + RESET, 7);
        imprimirPocoAPoco(AZUL + "  Escoge una de las" +
                " opciones anteriores, presiona \"1\", \"2\" o \"3\", seguido de un Enter.  " + RESET, 15);
        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            System.out.println("");
            if (opcion == 1) {
                audioPlayer.play("misterio.wav");
                Guia(scanner);
            }
            else if (opcion == 2) {

                IniciarJuego(scanner);
            }
            else if (opcion == 3) {

                InfinitoModo(scanner);
            }
            else {

                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                DeciciónInicial(scanner);
            }
        }
        else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);

            DeciciónInicial(scanner);
        }

    }
    private static void IniciarJuego(Scanner scanner){
        inicializarMatriz();
        MatrizVaritas();

        audioPlayer.setRepeat(true);
        audioPlayer.play("inicio.wav");

        borrarConsola(100);
        AgregarNombre();

        SeleccionarClase();
        imprimirPocoAPoco(AZUL + "  Presiona Enter para iniciar..." + RESET, 20);
        scanner.nextLine();
        System.out.println("  Cargando...");
        imprimirPocoAPoco(" . . .", 400);
        System.out.println("");
        borrarConsola(100);

        borrarConsola(100);


        if (!SeleccionarClaseCorrecto){
            System.out.println("");
            System.out.println(ROJO +"  Error en la selección de clase, lamentamos el inconveniente.");
            imprimirPocoAPoco(CYAN + "  Solucionando error... " + RESET, 10);
            imprimirPocoAPoco(" . . . . . . . . . . . . . . . . .", 400);
            System.out.println("");
            imprimirPocoAPoco(ROJO + "  Reiniciando... " + RESET, 10);
            imprimirPocoAPoco(" . . . . ¡No lo vuelvas a hacer! . . . . .", 200);
            borrarConsola(100);
            IniciarJuego(scanner);
        }

        mostrarInventario();


        imprimirPocoAPoco(AZUL + "  Presiona Enter para iniciar..." + RESET, 20);
        scanner.nextLine();
        System.out.println("  Cargando...");
        imprimirPocoAPoco(" . . .", 300);
        borrarConsola(1000);



        System.out.println("");
        audioPlayer.stop();
        audioPlayer.play("aves.wav");

        imprimirPocoAPoco("  En un pequeño pueblo al borde de un vasto y antiguo bosque,", 20);
        imprimirPocoAPoco("  vivía un joven aventurero llamado " + AMARILLO + NombreJugador + RESET + ".", 20);
        imprimirPocoAPoco("  Un día, decidió adentrarse en el Bosque Encantado,", 20);
        imprimirPocoAPoco("  una región llena de misterios y peligros, en busca de gloria y riqueza.", 20);
        System.out.println("");
        imprimirPocoAPoco("  El camino puede ser muy complicado, ¡mucha suerte!", 20);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para Continuar..." + RESET, 20);
        scanner.nextLine();

        inicio(scanner);


    }
    private static boolean InfinitoModo = false;
    private static int contadorCombates = 0;
    private static void RecompensasClaseMInfint() {
        if (ClaseInvocador && contadorCombates == 4){
            monstruosInvocados.add(3); // Dragón de Hielo
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con la invocación:  Dragón de Hielo" + RESET, 10);
        } else  if (ClaseInvocador && contadorCombates == 8){
            monstruosInvocados.add(4); // Guardián de Hierro
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con la invocación:  Guardián de Hierro" + RESET, 10);
        } else if (ClaseInvocador && contadorCombates == 10){
            monstruosInvocados.add(5); // Ángel Guardián
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con la invocación:  Ángel Guardián" + RESET, 10);
        } else if (ClaseInvocador && contadorCombates == 15){
            monstruosInvocados.add(6); // Elfo Sanador
            monstruosInvocados.add(7); // Vampiro Ancestral
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con la invocación:  Elfo Sanador" + RESET, 10);
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con la invocación:  Vampiro Ancestral" + RESET, 10);
        }else if (ClaseInvocador && contadorCombates == 20){

            monstruosInvocados.add(8); // Espectro Sombrío
            monstruosInvocados.add(9); // Nigromante Oscuro

            imprimirPocoAPoco(CYAN + "  * Se te recompensa con la invocación:  Espectro Sombrío" + RESET, 10);
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con la invocación:  Nigromante Oscuro" + RESET, 10);
        }

        if (ClaseMago && contadorCombates == 10){
            NivelBaston_1 = false;
            NivelBaston_2 = true;
            NivelBaston_3 = false;
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con:  Bastón del mago, nivel 2." + RESET, 10);
        } else if (ClaseMago && contadorCombates == 15){
            EnergiaVarita[0] += 5;
            MaxEnergiaVarita[0] = 15;
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con: Bastón del mago, Máx-Energia: 15" + RESET, 10);
        }
        else if (ClaseMago && contadorCombates == 20){
            NivelBaston_1 = false;
            NivelBaston_2 = false;
            NivelBaston_3 = true;
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con:  Bastón del mago, nivel 3." + RESET, 10);

        }


        if (ClaseGuerrero && contadorCombates == 6){
            HabilidadEscudoMagicoGuerrero = 8;
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con: Habilidad, Escudo mágico: 8" + RESET, 10);
        }
        else if (ClaseGuerrero && contadorCombates == 9){
            ReduccionGuerreroDañoObtenido = 0.7;
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con: Reducción de daño: 30%" + RESET, 10);
        }
        else if (ClaseGuerrero && contadorCombates == 15){
            ReduccionGuerreroDañoObtenido = 0.6;
            HabilidadEscudoMagicoGuerrero = 10;
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con: Reducción de daño: 60%" + RESET, 10);
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con: Habilidad, Escudo mágico: 10" + RESET, 10);
        }
        else if (ClaseGuerrero && contadorCombates == 20){
            HabilidadEscudoMagicoGuerrero = 17;
            imprimirPocoAPoco(CYAN + "  * Se te recompensa con: Habilidad, Escudo mágico: 17" + RESET, 10);
        }
    }
    private static void InfinitoModo(Scanner scanner) {
        inicializarMatriz();
        MatrizVaritas();
        InfinitoModo = true;
        audioPlayer.setRepeat(true);
        audioPlayer.play("elfos.wav");

        borrarConsola(100);
        AgregarNombre();

        SeleccionarClase();
        imprimirPocoAPoco(AZUL + "  Presiona Enter para iniciar..." + RESET, 20);
        scanner.nextLine();
        System.out.println("  Cargando...");
        imprimirPocoAPoco(" . . .", 400);
        System.out.println("");
        borrarConsola(100);

        // Verificar si la clase fue seleccionada correctamente
        if (!SeleccionarClaseCorrecto) {
            System.out.println("");
            System.out.println(ROJO +"  Error en la selección de clase, lamentamos el inconveniente.");
            imprimirPocoAPoco(CYAN + "  Solucionando error... " + RESET, 10);
            imprimirPocoAPoco(" . . . . . . . . . . . . . . . . .", 400);
            System.out.println("");
            imprimirPocoAPoco(ROJO + "  Reiniciando... " + RESET, 10);
            imprimirPocoAPoco(" . . . . . . . .", 200);
            borrarConsola(100);
            InfinitoModo(scanner); // Reiniciar si hay un error en la selección de clase
        }

        mostrarInventario();


        int combatesAntesComerciante = 3; // Número de combates antes de poder ver al comerciante por primera vez (se reduce 1 cada vez, hasta 0)
        int combatesComerciante = 3; // Número de combates que se necesitan, se incrementa y el otro toma este valor cuando llega a 0

        int gemasBase = 10; // Cantidad base de gemas aleatorias
        int incrementoGemas = 4;
        imprimirPocoAPoco(AZUL + "  Enter para continuar." + RESET, 15);
        scanner.nextLine();

        while (true) {
            contadorCombates++;
            RecompensasClaseMInfint();
            ImagenRandom(scanner);


            System.out.println("");
            imprimirPocoAPoco(AZUL + "  Enter para iniciar, combate #" + contadorCombates + RESET, 20);
            scanner.nextLine();  //scanner.nextLine();
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 300);
            borrarConsola(100);


            if (contadorCombates >= 0  && contadorCombates <= 4  ) {
                EnemigoGrupoSelec = GrupoE.BOSQUE ; EnemigoNivelSelec = 1; EnemigoIndiceSelec = -1; EnemigoCategoriaSelec = TipoSeleccion.ALEATORIO_BOSQUE;
                if (contadorCombates == 4){
                    EnemigoNivelSelec = 0;
                }
            }
            else if (contadorCombates > 4  && contadorCombates <= 9  ) {
                int opciones = (int) (Math.random() * 3) + 1;
                switch (opciones) {
                    case 1:
                        EnemigoGrupoSelec = GrupoE.BOSQUE ; EnemigoNivelSelec = 1; EnemigoIndiceSelec = -1; EnemigoCategoriaSelec = TipoSeleccion.ALEATORIO_BOSQUE;
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    default:

                        break;
                }
            }
            else if (contadorCombates > 9  && contadorCombates <= 12  ) {
                int opciones = (int) (Math.random() * 2) + 1;
                switch (opciones) {
                    case 1:

                        break;
                    case 2:

                        break;
                    default:

                        break;
                }
                if (contadorCombates == 10){ EnemigoNivelSelec = 0;}
            }
            else if (contadorCombates > 12  && contadorCombates <= 18  ) {
                int opciones = (int) (Math.random() * 3) + 1;
                switch (opciones) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    default:

                        break;
                }
            }
            else if (contadorCombates > 18  && contadorCombates <= 22  ) {
                int opciones = (int) (Math.random() * 4) + 1;
                switch (opciones) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    default:

                        break;
                }
                if (contadorCombates == 22){
                    EnemigoNivelSelec = 0;
                }
            }
            else {
                int opciones = (int) (Math.random() * 4) + 1;
                switch (opciones) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        EnemigoNivelSelec = 0;
                        break;
                    default:

                        break;
                }
            }



            SimularEncuentro();


            int GemasRandom = (int) (Math.random() * gemasBase) + 4;
            gemasBase += incrementoGemas;
            if (gemasBase >= 35 ) {
                gemasBase = 35;
            }


            Gemas += GemasRandom;
            imprimirPocoAPoco(MAGENTA + "  Felicidades, se te recompensa con +" + GemasRandom + " gemas." + RESET, 20);
            System.out.println("");

            combatesAntesComerciante--;
            if (combatesAntesComerciante <= 0) {
                mostrarimagen("comerciante.txt");
                System.out.println("");
                imprimirPocoAPoco(CYAN + "  ¡Momento de descanso!, ¿Quierés visitar al comerciante?" + RESET, 10);
                imprimirPocoAPoco(VERDE + "  (1. si) (2. no)" + RESET, 10);
                String descanso = scanner.nextLine().trim();
                switch (descanso) {
                    case "1":
                        borrarConsola(100);
                        comerciante();
                        break;
                    case "2":
                        imprimirPocoAPoco(AZUL + "  ¡Suerte!" + RESET, 10);
                        break;
                    default:
                        imprimirPocoAPoco(AZUL + "  ¡Suerte!    (*v*)" + RESET, 10);
                        return;
                }
                borrarConsola(1000);
                combatesComerciante +=2;
                if (combatesComerciante >= 6) {
                    combatesComerciante = 6;
                }
                combatesAntesComerciante = combatesComerciante;

                imprimirPocoAPoco( "  " , 20);
            }

        }

    }
    private static void ImagenRandom(Scanner scanner) {
        // Generar un número aleatorio dentro del rango de las imágenes disponibles
        int numeroAleatorio = (int) (Math.random() * 40) + 1; // 40 es el número total de imágenes (un par repetidas por error)

        // Usar un switch para seleccionar y mostrar la imagen correspondiente
        switch (numeroAleatorio) {
            case 1:
                mostrarimagen("guerrerochiqui.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Clase guerrero:" + RESET, 7);
                imprimirPocoAPoco(CYAN + "  * Un personaje con alta resistencia." + RESET, 7);
                break;
            case 2:
                mostrarimagen("magochiqui.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Clase mago:" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Personaje con uso continúo de mágia." + RESET, 7);
                break;
            case 3:
                mostrarimagen("invocadorchiqui.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Clase invocador:" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Un ser poderoso, ¡Nerfeen al invocador!" + RESET, 7);
                break;
            case 4:
                mostrarimagen("pvida.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[0] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¿Poca salud?, Aquí esta tu solución." + RESET, 7);
                break;
            case 5:
                mostrarimagen("pfuerza.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[1] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Con esto, podras ser más fuerte que tu adversario." + RESET, 7);
                break;
            case 6:
                mostrarimagen("pagilidad.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[2] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¿Quién necesita fuerza o salud, si no te pueden atacar?." + RESET, 7);
                break;
            case 7://Esta de mas
                mostrarimagen("pagilidad.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[2] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¿Quién necesita fuerza o salud, si no te pueden atacar?." + RESET, 7);
                break;
            case 8:
                mostrarimagen("mapa.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[3] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Preparate para la aventura." + RESET, 7);
                break;
            case 9:
                mostrarimagen("antorcha.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[4] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¿Alumbra o deslumbra?." + RESET, 7);
                break;
            case 10://Esta de más
                mostrarimagen("antorcha.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[4] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¿Alumbra o deslumbra?." + RESET, 7);
                break;
            case 11:
                mostrarimagen("cristal.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[5] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Un objeto mágico que brilla y otorga energía." + RESET, 7);
                break;
            case 12:
                mostrarimagen("bayas.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[6] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * En cantidad, te ayudan más." + RESET, 7);
                break;
            case 13:
                mostrarimagen("presistencia.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[7] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Con esto, los ataques enemigos te haran cosquillas." + RESET, 7);
                break;
            case 14:
                mostrarimagen("pvelocidad.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[8] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Eres más que agil con esto a tu poder." + RESET, 7);
                break;
            case 15:
                mostrarimagen("pdestreza.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[9] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¿Destreza?, Destreza es siempre acertar un golpe potente." + RESET, 7);
                break;
            case 16:
                mostrarimagen("pconcentracion.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[10] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¿Qué concentra?, La energía de tu cuerpo (¡No nos hacemos responsables por efectos secunduarios!)." + RESET, 7);
                break;
            case 17:
                mostrarimagen("frascotormenta.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[11] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Tormenta en un frasco, ¡que peligroso!." + RESET, 7);
                break;
            case 18:
                mostrarimagen("frascoveneno.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[12] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¡Cuidado!, no lo vallas a beber por error." + RESET, 7);
                break;
            case 19:
                mostrarimagen("stilitu.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[13] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Dibuja encantamientos a tu armadura." + RESET, 7);
                break;
            case 20:
                mostrarimagen("runa.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[14] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Incrusta piedras mágicas, que otorgan encantamientos mágicos." + RESET, 7);
                break;
            case 21:
                mostrarimagen("pergamino.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[15] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¡Poderosa mágia escrita en papel! ." + RESET, 7);
                break;
            case 22:
                mostrarimagen("kitreparacion.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  " + Objetos[16] + ":" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¿Equipamiento en malas condiciones?, ¡Ya no más!." + RESET, 7);
                break;
            case 23:
                mostrarimagen("bastonN.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Bastón del mago:" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Mucho poder, en un palo brillante." + RESET, 7);
                break;
            case 24:
                mostrarimagen("varitaFN.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Varita de fuego:" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¡Cuidado que quema!." + RESET, 7);
                break;
            case 25:
                mostrarimagen("varitaHN.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Varita de hielo:" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * No congela, pero relentiza." + RESET, 7);
                break;
            case 26:
                mostrarimagen("varitaDN.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Varita de debilidad:" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¿Te sientes débil?." + RESET, 7);
                break;
            case 27:
                mostrarimagen("varitaCN.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Varita de curación:" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Salud de emergencia." + RESET, 7);
                break;
            case 28:
                mostrarimagen("varitaPN.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Varita de protección:" + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Es asegurado, que no te pegan ni una." + RESET, 7);
                break;
            case 29:
                mostrarimagen("fenix.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[0] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¡Maravilloso!." + RESET, 7);
                break;
            case 30:
                mostrarimagen("golempiedra.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[1] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Rocas vivientes." + RESET, 7);
                break;
            case 31:
                mostrarimagen("espiritubosque.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[2] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Sus colores, son los colores del bosque." + RESET, 7);
                break;
            case 32:
                mostrarimagen("dragonhielo.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[3] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Tierno y peligroso." + RESET, 7);
                break;
            case 33:
                mostrarimagen("guardianhierro.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[4] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¡No te fies de el!" + RESET, 7);
                break;
            case 34:
                mostrarimagen("angelguardian.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[5] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Un ser poderoso." + RESET, 7);
                break;
            case 35:
                mostrarimagen("elfosanador.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[6] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Un medico, con entrenamiento en combate." + RESET, 7);
                break;
            case 36:
                mostrarimagen("vampiro.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[7] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * La sangre su alimento, tu cuello su objetivo." + RESET, 7);
                break;
            case 37:
                mostrarimagen("espectrosombrio.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[8] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Un fantasmasma y el ambiente helado." + RESET, 7);
                break;
            case 38:
                mostrarimagen("nigromante_oscuro.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[9] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * ¡No le dejes vivir mucho tiempo!." + RESET, 7);
                break;
            case 39:
                mostrarimagen("sombra_toxica.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[10] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Una criatura legendaria, afortunado aquel que lo vea." + RESET, 7);
                break;
            case 40:
                mostrarimagen("guerreroeterno.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Invocación: " + NombreMonstruo[11] + RESET, 7);
                imprimirPocoAPoco(CYAN + " * Un guerrero legendario forjado en las llamas del combate eterno.." + RESET, 7);
                break;
            default:
                // En caso de agregar más opciones en el futuro o error
                System.out.println("No se encontró una imagen para el número aleatorio generado.");
                break;
        }
    }



    //codigo inicial-----------------------------------------------------------------------------------------------------------------------------------
    private static void Guia(Scanner scanner) {
        borrarConsola(100);
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  ¿Qué te gustaria saber?", 5);
        imprimirPocoAPoco(VERDE + "  1. Información géneral.", 4);
        imprimirPocoAPoco("  2. Información sobre las clases.", 4);
        imprimirPocoAPoco("  3. Información sobre consumibles.", 4);
        imprimirPocoAPoco("  4. Información sobre varitas.", 4);
        imprimirPocoAPoco("  5. Información sobre invocaciones.", 4);
        imprimirPocoAPoco("  6. Información sobre el equipamiento y encantamientos.", 4);
        imprimirPocoAPoco(CYAN + "  0. Volver a las opciones anteriores." + RESET, 4);

        String opcion = scanner.nextLine().trim();
        switch (opcion) {
            case "1":
                InfoGeneral(scanner);
                break;
            case "2":
                InfoClases(scanner);
                break;
            case "3":
                InfoConsumibles(scanner);
                break;
            case "4":
                InfoVaritas(scanner);
                break;
            case "5":
                InfoInvocaciones(scanner);
                break;
            case "6":
            InfoEquipamiento(scanner);
                break;
            case "0":
                DeciciónInicial(scanner);
                break;
            default:
                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                scanner.nextLine();
                Guia(scanner);
                return;
        }
        Guia(scanner);

    }

    private static void InfoGeneral(Scanner scanner) {
        borrarConsola(100);
        System.out.println("");

        imprimirPocoAPoco(AMARILLO + "  Clases:" + RESET, 4);
        imprimirPocoAPoco("  + Todas las clases tienen una salud máxima establecida, así como un daño y agilidad específicos.", 4);
        imprimirPocoAPoco("  + Cada clase tiene sus propias ventajas y desventajas.", 4);
        imprimirPocoAPoco("  ", 4);

        imprimirPocoAPoco(AMARILLO + "  Consumibles:" + RESET, 4);
        imprimirPocoAPoco("  + Los consumibles se pueden usar antes, durante y después de un combate.", 4);
        imprimirPocoAPoco("  + Durante el combate, solo se puede usar un consumible después de 3 turnos.", 4);
        imprimirPocoAPoco("  ", 4);
        imprimirPocoAPoco("  *! Si usas un consumible para dañar al enemigo antes de que inicie el primer turno de combate,", 4);
        imprimirPocoAPoco("     no funcionará y perderás ese consumible.", 4);
        imprimirPocoAPoco("  ", 4);
        imprimirPocoAPoco("  + Los efectos de las pociones se cancelan al terminar el combate.", 4);
        imprimirPocoAPoco("  ", 4);
        imprimirPocoAPoco("  + La mayoria de pociones, se pueden aplicar a las invocaciones en combate.", 4);
        imprimirPocoAPoco("  ", 4);

        imprimirPocoAPoco(AMARILLO + "  Varitas:" + RESET, 4);
        imprimirPocoAPoco("  + Todas las clases, excepto el mago, deben esperar 3 turnos para usar una varita.", 4);
        imprimirPocoAPoco("  + Cada varita tiene un tiempo de recarga específico para volver a usar una de sus cargas.", 4);
        imprimirPocoAPoco("  + Los efectos de las varitas se cancelan al terminar el combate.", 4);
        imprimirPocoAPoco("  ", 4);

        imprimirPocoAPoco(AMARILLO + "  Invocaciones:" + RESET, 4);
        imprimirPocoAPoco("  + Solo puede haber una invocación en combate a la vez.", 4);
        imprimirPocoAPoco("  + Todas las invocaciones tienen una probabilidad de fallar al intentar ser invocadas.", 4);
        imprimirPocoAPoco("  + Al finalizar el combate, la invocación se retira automáticamente.", 4);
        imprimirPocoAPoco("  ", 4);

        imprimirPocoAPoco(AMARILLO + "  Equipamiento:" + RESET, 4);
        imprimirPocoAPoco("  + Todo el equipamiento de protección recibe el daño en lugar del jugador, siguiendo este orden: ", 4);
        imprimirPocoAPoco("    1º Escudo mágico, 2º Escudo físico, 3º Armadura, 4º Jugador.", 4);
        imprimirPocoAPoco("  ", 4);
        imprimirPocoAPoco("  + Si el jugador no posee algún tipo de equipamiento, se salta ese nivel de protección.", 4);
        imprimirPocoAPoco("  ", 4);
        imprimirPocoAPoco("  + Todos los equipamientos, excepto el escudo mágico, pueden ser encantados.", 4);
        imprimirPocoAPoco("  + Los encantamientos se eliminan si se adquiere otro equipamiento o si su durabilidad llega a 0.", 4);
        imprimirPocoAPoco("  ", 4);
        imprimirPocoAPoco("  + La espada es el único equipamiento que reduce su durabilidad en 1 cada turno en que el ataque del jugador sea exitoso.", 4);
        imprimirPocoAPoco("  ", 4);

        imprimirPocoAPoco(AMARILLO + "  Combate:" + RESET, 4);
        imprimirPocoAPoco("  + Los combates se rigen por turnos, siguiendo este orden:", 4);
        imprimirPocoAPoco("   Primero el jugador, luego la invocación (si hay), y por último el enemigo.", 4);
        imprimirPocoAPoco("  ", 4);
        imprimirPocoAPoco("  + La probabilidad de atacar del jugador, aliado o enemigo se mide de 0 a 10.", 4);
        imprimirPocoAPoco("  + Si la probabilidad de atacar es mayor o igual a la agilidad total del adversario, el ataque es exitoso; si no, el ataque falla.", 4);
        imprimirPocoAPoco("  ", 4);
        imprimirPocoAPoco("  + En ocasiones, el jugador deberá enfrentarse a varios enemigos, contando cada enemigo como un combate separado.", 4);
        imprimirPocoAPoco("  + El jugador debe planificar bien sus acciones, ya que los efectos activos e invocaciones se eliminan después de cada combate.", 4);
        System.out.println("");
        System.out.println(AZUL + "  Presiona Enter para regresar a las opciones anteriores." + RESET);
        scanner.nextLine();
        Guia(scanner);

    }
    private static void InfoClases(Scanner scanner){
        borrarConsola(100);
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Lista de clases:",7);
        imprimirPocoAPoco(VERDE + "  1. Información sobre la clase Guerrero.",7);
        imprimirPocoAPoco("  2. Información sobre la clase Mago.",7);
        imprimirPocoAPoco("  3. Información sobre  la clase Invocador." + RESET,7);
        imprimirPocoAPoco(CYAN + "  0. Volver a las opciones anteriores." + RESET,7);
        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            borrarConsola(100);
            System.out.println("");

            if (opcion == 1) {
                mostrarimagen("guerrerochiqui.txt");
                System.out.println("");
                imprimirPocoAPoco(MAGENTA + "  Clase Guerrero.",7);
                imprimirPocoAPoco( AMARILLO + "  Atributos:",7);
                imprimirPocoAPoco(RESET + "  * Salud máxima: " +  AtributosSaludClase[0],7);
                imprimirPocoAPoco("  * Agilidad: " + AtributosAgilidadClase[0],7);
                imprimirPocoAPoco("  * Daño base de clase: " + AtributosDañoClase[0],7);
                imprimirPocoAPoco("  * Probabilidad de critico: " +  (int)(AtributosCriticoClase[0] * 100) + "%",7);
                imprimirPocoAPoco("  * Reducción daño a espada: " +  (int)(AtributosReduccionDañoClase[0] * 100) + "%",7);
                imprimirPocoAPoco("  ",7);

                imprimirPocoAPoco(AMARILLO + "  Habilidades especiales de la clase:",7);
                imprimirPocoAPoco(RESET + "  ¡*! Redución del daño de espada: El daño de la espada se vera reducido al " + (int)(AtributosReduccionDañoClase[0] * 100) + "%.",7);
                imprimirPocoAPoco("  -> Ejemplo: La espada inflije 10 de daño, al ser de la clase guerrero este se reduce a " + ((int) (AtributosReduccionDañoClase[0] * 10)) + " de daño.",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco(RESET + "  * Redución del daño recibido: El daño aplicado al jugador sera el 80% del daño recibido.",7);
                imprimirPocoAPoco("  -> Ejemplo: el enemigo realiza un ataque de 10 de daño al jugador y el daño aplicado por reducción sera de 8 de daño.",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco("  * Escudo mágico: Despues de 5 turnos se aplicara un escudo mágico con 5 de resistencia",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco(CYAN + "  *! Estas habilidades y atributos, pueden ser mejoradas al completar ciertos eventos en la historia o subiendo de nivel.",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco(AMARILLO + "  Inventario inicial: el guerrero iniciara el juego con los siguientes objetos.",7);
                imprimirPocoAPoco(RESET + "  * Armadura: 5 de resistenccia",7);
                imprimirPocoAPoco("  * Poción de fuerza: 1",7);
                imprimirPocoAPoco("  * Poción de resistencia: 1",7);
                imprimirPocoAPoco("  * Poción de velocidad: 1",7);
                System.out.println("");
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();
            }
            else if (opcion == 2) {
                mostrarimagen("magochiqui.txt");
                System.out.println("");
                imprimirPocoAPoco(MAGENTA + "  Clase Mago.",7);
                imprimirPocoAPoco(AMARILLO + "  Atributos:",7);
                imprimirPocoAPoco(RESET + "  * Salud máxima: " +  AtributosSaludClase[1],7);
                imprimirPocoAPoco("  * Agilidad: " + AtributosAgilidadClase[1],7);
                imprimirPocoAPoco("  * Daño base de clase: " + AtributosDañoClase[1],7);
                imprimirPocoAPoco("  * Probabilidad de critico: " +  (int)(AtributosCriticoClase[1] * 100) + "%",7);
                imprimirPocoAPoco("  * Reducción daño a espada: " +  (int)(AtributosReduccionDañoClase[1] * 100) + "%",7);
                imprimirPocoAPoco("  ",7);

                imprimirPocoAPoco(AMARILLO + "  Habilidades especiales de la clase:",7);
                imprimirPocoAPoco(RESET + "  ¡*! Redución del daño de espada: El daño de la espada se vera reducido al " +  (int)(AtributosReduccionDañoClase[1] * 100) + "%.",7);
                imprimirPocoAPoco("  -> Ejemplo: La espada inflije 10 de daño, al ser de la clase mago este se reduce a " + ((int) (AtributosReduccionDañoClase[1] * 10)) + " de daño.",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco("  * Uso continúo de mágia: la clase mago puede usar el baston del mago o una de las varitas obtenidas, cada turno de combate.",7);
                imprimirPocoAPoco("  -> Otras clases deberan esperar 3 turnos de combate para poder usar una de las varitas obtenidas.",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco(CYAN + "  *! El baston del mago puede ser mejorado al completar ciertos eventos en la historia",7);
                imprimirPocoAPoco(CYAN + "  *! Estas habilidades y atributos, pueden ser mejoradas al completar ciertos eventos en la historia o subiendo de nivel.",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco(AMARILLO + "  Inventario inicial: el mago iniciara el juego con los siguientes objetos.",7);
                imprimirPocoAPoco(RESET + "  * Baston del mago nivel 1",7);
                imprimirPocoAPoco("  * Frasco de veneno: 1",7);
                imprimirPocoAPoco("  * Cristal de mana: 1",7);
                System.out.println("");
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();


            }
            else if (opcion == 3) {
                mostrarimagen("invocadorchiqui.txt");
                System.out.println("");
                imprimirPocoAPoco(MAGENTA + "  Clase Invocador.",7);
                imprimirPocoAPoco(AMARILLO + "  Atributos:",7);
                imprimirPocoAPoco(RESET + "  * Salud máxima: " +  AtributosSaludClase[2],7);
                imprimirPocoAPoco("  * Agilidad: " + AtributosAgilidadClase[2],7);
                imprimirPocoAPoco("  * Daño base de clase: " + AtributosDañoClase[2],7);
                imprimirPocoAPoco("  * Probabilidad de critico: " +  (int)(AtributosCriticoClase[2] * 100) + "%",7);
                imprimirPocoAPoco("  * Reducción daño a espada: " +  (int)(AtributosReduccionDañoClase[2] * 100) + "%",7);
                imprimirPocoAPoco("  ",7);

                imprimirPocoAPoco(AMARILLO + "  Habilidades especiales de la clase:",7);
                imprimirPocoAPoco(RESET + "  ¡*! Redución del daño de espada: El daño de la espada se vera reducido al " +  (int)(AtributosReduccionDañoClase[2] * 100) + "%.",7);
                imprimirPocoAPoco("  -> Ejemplo: La espada inflije 10 de daño, al ser de la clase invocador este se reduce a " + ((int) (AtributosReduccionDañoClase[2] * 10)) + " de daño.",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco("  * Uso de invocaciones: la clase invocador puede traer a un aliado en medio del combate. ",7);
                imprimirPocoAPoco("  ¡-¡ Solo se puede invocar a un aliado, si no hay uno en medio del combate despues de 3 turno.",7);
                imprimirPocoAPoco("  ¡-¡ Cada invocacón tiene sus propios atributos y una probabilidad de fallar la invocación.",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco(CYAN + "  *! El invocador puede obtener invocaciones adicionales al completar ciertos eventos en la historia",7);
                imprimirPocoAPoco(CYAN + "  *! Estas habilidades y atributos, pueden ser mejoradas al completar ciertos eventos en la historia o subiendo de nivel.",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco(AMARILLO + "  Inventario inicial: el invocador iniciara el juego con los siguientes objetos.",7);
                imprimirPocoAPoco(RESET + "  * Frasco de tormenta: 1",7);
                imprimirPocoAPoco("  * Poción de fuerza: 1",7);
                imprimirPocoAPoco("  ",7);
                imprimirPocoAPoco(AMARILLO + "  Invocaciones iniciales:",7);
                imprimirPocoAPoco(RESET + "  * Fenix",7);
                imprimirPocoAPoco("  * Golen de piedra",7);
                imprimirPocoAPoco("  * Espiritu del bosque",7);
                System.out.println("");
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();
            }
            else if (opcion == 0) {
                Guia(scanner);
            }
            else {

                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                InfoClases(scanner);
            }
        }
        else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);

            InfoClases(scanner);
        }
        InfoClases(scanner);
    }
    private static void InfoConsumibles(Scanner scanner) {
        borrarConsola(100);
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Lista de consumibles:", 7);
        imprimirPocoAPoco(VERDE + "   1. Información sobre la Poción de curación.", 4);
        imprimirPocoAPoco("   2. Información sobre la Poción de fuerza.", 4);
        imprimirPocoAPoco("   3. Información sobre la Poción de agilidad.", 4);
        imprimirPocoAPoco("   4. Información sobre el Mapa.", 4);
        imprimirPocoAPoco("   5. Información sobre la Antorcha.", 4);
        imprimirPocoAPoco("   6. Información sobre el Cristal de mana.", 4);
        imprimirPocoAPoco("   7. Información sobre las Bayas.", 4);
        imprimirPocoAPoco("   8. Información sobre la Poción de resistencia.", 4);
        imprimirPocoAPoco("   9. Información sobre la Poción de velocidad.", 4);
        imprimirPocoAPoco("  10. Información sobre la Poción de destreza.", 4);
        imprimirPocoAPoco("  11. Información sobre la Poción de concentración.", 4);
        imprimirPocoAPoco("  12. Información sobre el Frasco de tormenta.", 4);
        imprimirPocoAPoco("  13. Información sobre el Frasco de veneno.", 4);
        imprimirPocoAPoco("  14. Información sobre el Stilus arcano.", 4);
        imprimirPocoAPoco("  15. Información sobre la Runa de los antiguos.", 4);
        imprimirPocoAPoco("  16. Información sobre el Pergamino del arcanum.", 4);
        imprimirPocoAPoco("  17. Información sobre el Kit de reparación.", 4);
        imprimirPocoAPoco("  18. Información sobre el Pergamino de invocación.", 4);
        imprimirPocoAPoco("  19. Información sobre la Poción de curación grande.", 4);
        imprimirPocoAPoco(CYAN + "   0. Volver a las opciones anteriores." + RESET, 4);

        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            borrarConsola(100);
            System.out.println("");
            switch (opcion) {
                case 1:
                    mostrarimagen("pvida.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Poción de curación:", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Restaura 25 puntos de salud del jugador.", 7);
                    imprimirPocoAPoco("  *! No puede superar la salud máxima de la clase.", 7);
                    break;
                case 2:
                    mostrarimagen("pfuerza.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Poción de fuerza.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Incrementa +4 de daño del jugador durante 6 turnos.", 7);
                    imprimirPocoAPoco("  *! Usar otra poción no acumulara el efecto, la durabilidad sera restaurado para que dure 6 turnos.", 5);
                    break;
                case 3:
                    mostrarimagen("pagilidad.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Poción de agilidad.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Incrementa +3 de agilidad durante 6 turnos.", 7);
                    imprimirPocoAPoco("  *! Usar otra poción no acumulara el efecto, la durabilidad sera restaurado para que dure 6 turnos.", 5);
                    break;
                case 4:
                    mostrarimagen("mapa.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Mapa.", 7);
                    imprimirPocoAPoco(RESET + "  * No es un consumible, pero sirve para una aventura.", 7);
                    break;
                case 5:
                    mostrarimagen("antorcha.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Antorcha.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Ilumina áreas oscuras, incrementa +1 de agilidad durante 5 turnos.", 5);
                    imprimirPocoAPoco("  *! Usar otra antorcha no acumulara el efecto, la durabilidad sera restaurado para que dure 5 turnos.", 5);
                    break;
                case 6:
                    mostrarimagen("cristal.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Cristal de mana.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Restaura puntos de mana/energía a una varita o bastón del mago obtenida.", 5);
                    imprimirPocoAPoco("  -> Los puntos restaurados dependen de la opción escogida.", 5);
                    imprimirPocoAPoco("  *! No puede superar el mana/energía máxima de la varita o baston del mago.", 5);
                    break;
                case 7:

                    mostrarimagen("bayas.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Bayas.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Restaura 6 puntos de salud del jugador.", 5);
                    imprimirPocoAPoco("  *! No puede superar la salud máxima de la clase.", 5);
                    System.out.println("");

                    break;
                case 8:
                    mostrarimagen("presistencia.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Poción de resistencia.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Reduce 20% del daño físico y 50% del daño mágico recibido durante 6 turnos. ", 5);
                    imprimirPocoAPoco("  -> Ejemplo: Si el jugador recibe 10 de daño, solo se le aplicara 8 de daño. ", 5);
                    imprimirPocoAPoco("  *! Su efecto solo sera aplicado cuando el daño sea al jugador, la armadura y escudos son absueltos de dicho efecto.", 5);
                    imprimirPocoAPoco("  *! Usar otra poción no acumulara el efecto, la durabilidad sera restaurado para que dure 6 turnos.", 5);
                    break;
                case 9:

                    mostrarimagen("pvelocidad.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Poción de velocidad.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Permite al jugador realizar un segundo ataque en el mismo turno, durante 3 turnos", 5);
                    imprimirPocoAPoco("  *! Usar otra poción no acumulara el efecto, la durabilidad sera restaurado para que dure 3 turnos.", 5);
                    break;
                case 10:
                    mostrarimagen("pdestreza.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Poción de destreza.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: El jugador realizara el daño total máximo durante 5 turnos.", 5);
                    imprimirPocoAPoco("  -> Ejemplo: Daño total: 4 - 9, Mientras dure el efecto siempre realizara 9 de daño. ", 5);
                    imprimirPocoAPoco("  *! Usar otra poción no acumulara el efecto, la durabilidad sera restaurado para que dure 5 turnos.", 5);
                    break;
                case 11:
                    mostrarimagen("pconcentracion.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Poción de concentración.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Las varitas obtenidas recargan el doble de rápido durante 6 turnos.", 5);
                    imprimirPocoAPoco("  *! Usar otra poción no acumulara el efecto, la durabilidad sera restaurado para que dure 6 turnos.", 5);

                    break;
                case 12:
                    mostrarimagen("frascotormenta.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Frasco de tormenta.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Aplica 40 de daño al enemigo de forma instantanea", 5);
                    imprimirPocoAPoco("  *! Provabilidad de dañar al jugador 33%, aplica al jugador 20 de daño de forma instantanea.", 5);
                    imprimirPocoAPoco("  -> Si la salud del jugador es 30 o menos, se preguntara al jugador si esta seguro de usar este consumible.", 5);
                    break;
                case 13:
                    mostrarimagen("frascoveneno.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Frasco de veneno.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Aplica daño al enemigo igual al valor del veneno, este valor se reduce en 1 cada turno.", 5);
                    imprimirPocoAPoco("  -> Ejemplo: Turno 1: Valor de veneno = 5, daño a enemigo 5, turno 2: Valor de veneno = 4, daño a enemigo 4... ", 5);
                    imprimirPocoAPoco("  ** El efecto de este consumible se puede acumular, sumando el valor restante con el valor original.", 5);
                    break;
                case 14:
                    mostrarimagen("stilitu.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Stilus arcano.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Puede encantar la armadura, el encantamiento otorgado es aleatorio y aumenta su durabilidad en +5.", 5);
                    imprimirPocoAPoco("  *! Si el encantamiento a otorgar ya esta imbuido en la armadura previamente, solo se descartara este consumible.", 5);
                    imprimirPocoAPoco("  *! Si la durabilidad de la armadura llega a 0 ó se remplaza comprando, los encantamientos seran anulados.", 5);
                    break;
                case 15:
                    mostrarimagen("runa.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Runa de los antiguos.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Puede encantar el escudo, el encantamiento otorgado es aleatorio y aumenta su durabilidad en +3.", 5);
                    imprimirPocoAPoco("  *! Si el encantamiento a otorgar ya esta imbuido en el escudo previamente, solo se descartara este consumible.", 5);
                    imprimirPocoAPoco("  *! Si la durabilidad del escudo llega a 0 ó se remplaza comprando, los encantamientos seran anulados.", 5);
                    break;
                case 16:
                    mostrarimagen("pergamino.txt");
                    System.out.println("");
                    imprimirPocoAPoco(MAGENTA + "  Pergamino del arcanum.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Puede encantar la espada, el encantamiento otorgado es aleatorio y aumenta su durabilidad en +2.", 5);
                    imprimirPocoAPoco("  *! Si el encantamiento a otorgar ya esta imbuido en la espada previamente, solo se descartara este consumible.", 5);
                    imprimirPocoAPoco("  *! Si la durabilidad de la espada llega a 0 ó se remplaza comprando, los encantamientos seran anulados.", 5);
                    break;
                case 17:
                    mostrarimagen("kitreparacion.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Kit de reparación.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Aumenta la durabilidad de un equipamiento...", 7);
                    imprimirPocoAPoco("  Armadura: +20, Escudo: +10, Espada: +8", 7);
                    imprimirPocoAPoco("  *! Solo se puede aumentar la durabilidad de un mismo equipamiento 2 veces.", 5);
                    imprimirPocoAPoco("  -> Si un equipamiento es remplazado, este podra aumentar su durabilidad 2 veces.", 5);
                    imprimirPocoAPoco("  -> Ejemplo: El jugador posee una armadura con 15 de durabilidad y no puede ser reparado más veces...", 5);
                    imprimirPocoAPoco("     El jugador compra o consigue una nueva armadura, este tiene 50 de durabilidad...", 5);
                    imprimirPocoAPoco("     Este si puede aumentar su durabilidad 2 veces, obteniendo así hasta +40 de durabilidad, total: 90 de durabilidad.", 5);
                    break;
                case 18:
                    mostrarimagen("pergaminoinvocacion.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Pergamino de invocación.", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Invoca a un alidado (Guerrero eterno) durante un combate.", 7);
                    imprimirPocoAPoco("  *! Solo se puede utilizar si no hay una invocación en combate", 5);
                    imprimirPocoAPoco("  *! Probabilidad de fallar invocación: 10%", 5);
                    imprimirPocoAPoco("  *! Esta invocación seguira las reglas establecidas para las invocaciones en combate.", 5);
                    imprimirPocoAPoco("    ", 5);

                    break;
                case 19:
                    mostrarimagen("pcuraciongrande.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Poción de curación grande:", 7);
                    imprimirPocoAPoco(RESET + "  * Efecto: Restaura todos los puntos de salud perdidos del jugador.", 7);
                    imprimirPocoAPoco("  *! No puede superar la salud máxima de la clase.", 7);
                    imprimirPocoAPoco("  *! No se puede conseguir con el comerciante.", 7);
                    imprimirPocoAPoco("  *! No se puede utilizar en las invocaciones.", 7);
                    break;
                case 0:
                    Guia(scanner);
                    return;
                default:
                    imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                    InfoConsumibles(scanner);
                    return;
            }
            System.out.println("");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            InfoConsumibles(scanner);
        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);
            InfoConsumibles(scanner);
        }
        InfoConsumibles(scanner);
    }
    private static void InfoVaritas(Scanner scanner){
        borrarConsola(100);
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Lista de varitas: ", 7);
        imprimirPocoAPoco(VERDE + "  1. Bastón del Mago.", 7);
        imprimirPocoAPoco("  2. Varita de Fuego.", 7);
        imprimirPocoAPoco("  3. Varita de Hielo.", 7);
        imprimirPocoAPoco("  4. Varita de Debilidad.", 7);
        imprimirPocoAPoco("  5. Varita de Curación.", 7);
        imprimirPocoAPoco("  6. Varita de Protección.", 7);
        imprimirPocoAPoco(CYAN + "  0. Volver a las opciones anteriores." + RESET, 7);

        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            borrarConsola(100);

            switch (opcion) {
                case 1:
                    mostrarimagen("bastonN.txt");
                    System.out.println("");
                    imprimirPocoAPoco(MAGENTA + "  Bastón del mago" + RESET, 7);
                    imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 7);
                    imprimirPocoAPoco("  El Bastón del Mago es una poderosa herramienta mágica.", 7);
                    imprimirPocoAPoco("  Otorga la capacidad de conjurar diferentes hechizos.", 7);
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Atributos:" + RESET, 7);
                    imprimirPocoAPoco("  * Energía/mana: 10", 7);
                    imprimirPocoAPoco("  * Hechizos en total: 5", 7);
                    imprimirPocoAPoco("  -> Turnos de recarga: 3 turnos por carga." + RESET, 7);
                    System.out.println("");
                    imprimirPocoAPoco(CYAN + "  *! El bastón del mago puede ser mejorado tanto en su nivel, como cantidad de energía/mana máximo." + RESET, 7);
                    System.out.println("");
                    imprimirPocoAPoco(AZUL + "  Selecciona el nivel del Bastón del Mago: ", 7);
                    imprimirPocoAPoco(VERDE + "  1. Nivel 1", 5);
                    imprimirPocoAPoco("  2. Nivel 2", 5);
                    imprimirPocoAPoco("  3. Nivel 3" + RESET, 5);


                    String nivel = scanner.nextLine().trim();

                    switch (nivel){
                        case "1":
                            imprimirPocoAPoco(AMARILLO + "  Bastón del mago, nivel 1" + RESET, 5);
                            imprimirPocoAPoco("  * Dañar al enemigo, +6 daño a enemigo," + RESET + " -3 de energía.", 3);
                            imprimirPocoAPoco("  * Aumentar tu agilidad en +2," + RESET + " -1 de energía.", 3);
                            imprimirPocoAPoco(CYAN + "  -> Duración de efecto: 3 turnos." + RESET, 7);
                            imprimirPocoAPoco("  * Aumentar tu fuerza en +5," + RESET + " -2 de energía.", 3);
                            imprimirPocoAPoco(CYAN + "  -> Duración de efecto: 3 turnos." + RESET, 7);
                            imprimirPocoAPoco("  * Obtener +7 de escudo mágico," + RESET + " -3 de energía.", 3);
                            imprimirPocoAPoco("  * Sanar tus heridas +5 de salud," + RESET + " -2 de energía." + RESET, 3);

                            break;
                        case "2":
                            imprimirPocoAPoco(AMARILLO + "  Bastón del mago, nivel 2" + RESET, 5);
                            imprimirPocoAPoco("  * Dañar al enemigo, +10 daño a enemigo," + RESET + " -3 de energía.", 3);
                            imprimirPocoAPoco("  * Aumentar tu agilidad en +3," + RESET + " -1 de energía.", 3);
                            imprimirPocoAPoco(CYAN + "  -> Duración de efecto: 3 turnos." + RESET, 7);
                            imprimirPocoAPoco("  * Aumentar tu fuerza en +7," + RESET + " -2 de energía.", 3);
                            imprimirPocoAPoco(CYAN + "  -> Duración de efecto: 3 turnos." + RESET, 7);
                            imprimirPocoAPoco("  * Obtener +15 de escudo mágico," + RESET + " -4 de energía.", 3);
                            imprimirPocoAPoco("  * Sanar tus heridas +7 de salud," + RESET + " -2 de energía." + RESET, 3);

                            break;
                        case "3":
                            imprimirPocoAPoco(AMARILLO + "  Bastón del mago, nivel 3" + RESET, 5);
                            imprimirPocoAPoco("  * Dañar al enemigo, +12 daño a enemigo y Sanar tus heridas +6 de salud," + RESET + " -4 de energía.", 3);
                            imprimirPocoAPoco("  * Aumentar tu agilidad en +3 y tu fuerza en +7," + RESET + " -3 de energía.", 3);
                            imprimirPocoAPoco(CYAN + "  -> Duración de efecto: 3 turnos." + RESET, 7);
                            imprimirPocoAPoco("  * Obtener +20 de escudo mágico," + RESET + " -4 de energía.", 3);
                            imprimirPocoAPoco("  * Exploción mágica," + RESET + " usa toda la energía.", 3);

                            break;
                        default:
                            imprimirPocoAPoco(AMARILLO + "  Bastón del mago, nivel 4 o superior inexistente." + RESET, 5);
                            imprimirPocoAPoco("  (*_*)",5);
                            scanner.nextLine();
                            InfoVaritas(scanner);
                            return;
                    }
                    break;
                case 2:
                    mostrarimagen("varitaFN.txt");
                    System.out.println("");
                    imprimirPocoAPoco(MAGENTA + "  Varita de fuego." + RESET, 7);
                    imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 7);
                    imprimirPocoAPoco("  La Varita de Fuego lanza proyectiles ígneos que pueden quemar a los enemigos, causando daño continuo.", 5);
                    imprimirPocoAPoco(RESET + "  * Efecto: Aplica daño al enemigo igual al valor de quemadura, este valor se reduce en 1 cada turno.", 5);
                    imprimirPocoAPoco("  -> Ejemplo: Turno 1: Valor de quemadura = 4, daño a enemigo 4, turno 2: Valor de veneno = 3, daño a enemigo 3... ", 5);
                    imprimirPocoAPoco("  ** El valor de la quemadura puede ser acumulado.", 5);

                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Atributos:" + RESET, 7);
                    imprimirPocoAPoco("  * Energía/mana: 4", 7);
                    imprimirPocoAPoco("  * Valor de quemadura: +" + DañoVarita[1], 7);
                    imprimirPocoAPoco(CYAN + "  -> Duración de efecto: variable." + RESET, 7);
                    imprimirPocoAPoco("  -> Turnos de recarga: 7 turnos por carga.", 7);


                    break;
                case 3:
                    mostrarimagen("varitaHN.txt");
                    System.out.println("");
                    imprimirPocoAPoco(MAGENTA + "  Varita de hielo." + RESET, 7);
                    imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 7);
                    imprimirPocoAPoco("  La Varita de Hielo congela a los enemigos, ralentizando sus movimientos, dificultando la capacidad de esquivar.", 5);
                    imprimirPocoAPoco(RESET + "  * Efecto: Disminuye la capacidad de esquivar del enemigo.", 5);
                    imprimirPocoAPoco("  -> Ejemplo: Turno 1: Agilidad enemigo: 10, agilidad final: 8, Turno 2: Agilidad enemigo: 3, agilidad final 1", 5);
                    imprimirPocoAPoco("  *! El efecto de esta varita, no es acumulable.", 5);

                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Atributos:" + RESET, 7);
                    imprimirPocoAPoco("  * Energía/mana: 4", 7);
                    imprimirPocoAPoco("  * Agilidad a enemigo: -2", 7);
                    imprimirPocoAPoco(CYAN + "  -> Duración de efecto: 3 turnos." + RESET, 7);
                    imprimirPocoAPoco("  -> Turnos de recarga: 7 turnos por carga.", 7);

                    break;
                case 4:
                    mostrarimagen("varitaDN.txt");
                    System.out.println("");
                    imprimirPocoAPoco(MAGENTA + "  Varita de debilidad." + RESET, 7);
                    imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 7);
                    imprimirPocoAPoco("  La Varita de Debilidad, dificulta la capacidad de acertar el ataque del enemigo y reduce su daño.", 5);
                    imprimirPocoAPoco(RESET + "  * Efecto: Disminuye la capacidad de atacar del enemigo y reduce su daño en -3.", 5);
                    imprimirPocoAPoco("  -> Ejemplo: Turno 1: Probabilidad de acierto: 10, probabilidad final: 8, Turno 2: Probabilidad de acierto: 3, probabilidad final 1", 5);
                    imprimirPocoAPoco("  *! El efecto de esta varita, no es acumulable.", 5);

                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Atributos:" + RESET, 7);
                    imprimirPocoAPoco("  * Energía/mana: 4", 7);
                    imprimirPocoAPoco("  * Probabilidad de golpear del enemigo: -2", 7);
                    imprimirPocoAPoco("  * Daño del enemigo: -3", 7);
                    imprimirPocoAPoco(CYAN + "  -> Duración de efecto: 3 turnos." + RESET, 7);
                    imprimirPocoAPoco("  -> Turnos de recarga: 7 turnos por carga.", 7);

                    break;
                case 5:
                    mostrarimagen("varitaCN.txt");
                    System.out.println("");
                    imprimirPocoAPoco(MAGENTA + "  Varita de curación." + RESET, 7);
                    imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 7);
                    imprimirPocoAPoco("  La Varita de Curación restaura la salud del jugador, permitiendo una recuperación rápida en combate.", 5);
                    imprimirPocoAPoco(RESET + "  * Efecto: recupera parte de la salud perdida del jugador.", 5);
                    imprimirPocoAPoco("  *! El efecto de esta varita, no puede superar la salud máxima del jugador.", 5);

                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Atributos:" + RESET, 7);
                    imprimirPocoAPoco("  * Energía/mana: 2", 7);
                    imprimirPocoAPoco("  * Curación: +" + DañoVarita[4], 7);
                    imprimirPocoAPoco(CYAN + "  -> Duración de efecto: instantaneo." + RESET, 7);
                    imprimirPocoAPoco("  -> Turnos de recarga: 7 turnos por carga.", 7);

                    break;
                case 6:
                    mostrarimagen("varitaPN.txt");
                    System.out.println("");
                    imprimirPocoAPoco(MAGENTA + "  Varita de protección." + RESET, 7);
                    imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 7);
                    //imprimirPocoAPoco("      La Varita de Protección crea escudos mágicos que absorben el daño, protegiendo a los aliados.", 5);
                    imprimirPocoAPoco("  La Varita de Protección, asegura que el jugador no sea atacado por el enemigo.", 5);
                    imprimirPocoAPoco(RESET + "  * Efecto: Aumenta la agilidad del jugador de forma abrupta.", 5);
                    imprimirPocoAPoco("  *! El efecto de esta varita no anula el daño a jugador por otros medios.", 5);

                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Atributos:" + RESET, 7);
                    imprimirPocoAPoco("  * Energía/mana: 4", 7);
                    imprimirPocoAPoco("  * Agilidad de jugador: +15", 7);
                    imprimirPocoAPoco(CYAN + "  -> Duración de efecto: 1 turno." + RESET, 7);
                    imprimirPocoAPoco("  -> Turnos de recarga: 7 turnos por carga.", 7);

                    break;
                case 0:
                    Guia(scanner);
                    return;
                default:
                    imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                    scanner.nextLine();
                    InfoVaritas(scanner);
                    return;


            }
            System.out.println("");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            InfoVaritas(scanner);

        }
        else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);

            InfoVaritas(scanner);
        }
    }
    private static void InfoInvocaciones(Scanner scanner) {
        borrarConsola(100);
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Lista de invocaciones: ", 7);
        imprimirPocoAPoco(VERDE + "   1. Información sobre el Fénix.", 5);
        imprimirPocoAPoco("   2. Información sobre el Gólem de Piedra.", 5);
        imprimirPocoAPoco("   3. Información sobre el Espíritu del Bosque.", 5);
        imprimirPocoAPoco("   4. Información sobre el Dragón de Hielo.", 5);
        imprimirPocoAPoco("   5. Información sobre el Guardián de Hierro.", 5);
        imprimirPocoAPoco("   6. Información sobre el Ángel Guardián.", 5);
        imprimirPocoAPoco("   7. Información sobre el Elfo Sanador.", 5);
        imprimirPocoAPoco("   8. Información sobre el Vampiro Ancestral.", 5);
        imprimirPocoAPoco("   9. Información sobre el Espectro Sombrío.", 5);
        imprimirPocoAPoco("  10. Información sobre el Nigromante Oscuro.", 5);
        imprimirPocoAPoco("  11. Información sobre Sombra Tóxica.", 5);
        imprimirPocoAPoco("  12. Información sobre Guerrero eterno.", 5);
        imprimirPocoAPoco(CYAN + "   0. Volver a las opciones anteriores." + RESET, 5);

        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea
            borrarConsola(100);
            System.out.println("");

            switch (opcion) {
                case 1:
                    mostrarimagen("fenix.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Fénix:" + RESET, 7);
                    imprimirPocoAPoco("  El Fénix es una majestuosa ave de fuego, capaz de infligir daño con sus llamas ardientes.", 5);
                    imprimirPocoAPoco("  Con plumaje resplandeciente y alas que arden con el fuego eterno, el Fénix domina el cielo con su presencia imponente.", 4);
                    imprimirPocoAPoco("  Posee un daño considerable, convirtiendo a sus enemigos en cenizas con cada ráfaga de fuego que desata.", 4);
                    imprimirPocoAPoco("  Aunque su salud es aceptable, su capacidad para controlar el elemento del fuego le otorga una ventaja formidable en combate.", 4);
                    imprimirPocoAPoco("  A diferencia de lo que se pueda considerar, esta criatura no renacerá de sus cenizas; más bien, su esencia ardiente es inmortal y eterna.", 4);
                    imprimirPocoAPoco("  El calor intenso que emana de su cuerpo puede derretir incluso las defensas más robustas, haciendo que sus enemigos teman su ira ardiente.", 4);
                    imprimirPocoAPoco("  Los cielos se iluminan con su vuelo majestuoso, y el resplandor de sus llamas guía a sus aliados hacia la victoria.", 4);
                    imprimirPocoAPoco("  En el campo de batalla, el Fénix es un símbolo de poder y renacimiento, recordando a todos que el fuego puede ser tanto destructor como purificador.", 4);

                    System.out.println("");
                    imprimirInfoMonstruo(0);
                    break;
                case 2:
                    mostrarimagen("golempiedra.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Gólem de Piedra:" + RESET, 7);
                    imprimirPocoAPoco("  El Gólem de Piedra es una criatura robusta formada de rocas, con gran resistencia y fuerza bruta.", 5);
                    imprimirPocoAPoco("  Surgido de las profundidades de la tierra, cada una de sus partes está compuesta por rocas ancestrales que le confieren una durabilidad inigualable.", 4);
                    imprimirPocoAPoco("  Con un daño medio, el Gólem de Piedra aplasta a sus enemigos con golpes poderosos y precisos.", 4);
                    imprimirPocoAPoco("  Su salud alta le permite soportar los ataques más feroces, actuando como un muro impenetrable en el campo de batalla.", 4);
                    imprimirPocoAPoco("  Cada movimiento de su masiva estructura resuena con el peso de la tierra misma, intimidando a cualquiera que se le oponga.", 4);
                    imprimirPocoAPoco("  Aunque no es rápido, su presencia imponente y su capacidad para aguantar daños lo convierten en un adversario temido y respetado.", 4);
                    imprimirPocoAPoco("  Los aliados del Gólem de Piedra confían en su fortaleza para mantener la línea del frente, sabiendo que este coloso de piedra no se rendirá fácilmente.", 4);
                    imprimirPocoAPoco("  Su fuerza bruta y resistencia hacen del Gólem de Piedra una pieza esencial en cualquier equipo que busque la victoria a través de la tenacidad y la fuerza.", 4);

                    imprimirInfoMonstruo(1);
                    break;
                case 3:
                   mostrarimagen("espiritubosque.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Espíritu del Bosque:" + RESET, 7);
                    imprimirPocoAPoco("  El Espíritu del Bosque es una entidad mística que protege la naturaleza y ayuda a su equipo con habilidades de curación.", 5);
                    imprimirPocoAPoco("  Nacido del corazón de los bosques antiguos, su conexión con la tierra y las plantas es profunda e inquebrantable.", 4);
                    imprimirPocoAPoco("  Con un daño y salud bajos, su verdadera fortaleza reside en su increíble habilidad para sanar heridas y revitalizar a sus aliados.", 4);
                    imprimirPocoAPoco("  Su presencia en el campo de batalla trae consigo la esencia de la naturaleza, invocando la energía de los árboles y las flores para curar y proteger.", 4);
                    imprimirPocoAPoco("  Aunque no es un guerrero formidable en términos de combate directo, su apoyo constante y su habilidad para mantener a su equipo en pie lo convierten en una pieza clave.", 4);
                    imprimirPocoAPoco("  La serenidad y sabiduría que emana inspiran a sus compañeros, dándoles fuerza y determinación para seguir adelante.", 4);
                    imprimirPocoAPoco("  Aquellos que busquen destruir la naturaleza se encontrarán con su resistencia tranquila pero poderosa, capaz de revertir el curso de una batalla con su toque curativo.", 4);

                    imprimirInfoMonstruo(2);
                    break;
                case 4:
                   mostrarimagen("dragonhielo.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Dragón de Hielo:" + RESET, 7);
                    imprimirPocoAPoco("  El Dragón de Hielo es un temible reptil alado que puede congelar a sus enemigos con su aliento gélido.", 5);
                    imprimirPocoAPoco("  Emergiendo de los picos más altos y fríos, su presencia es anunciada por un viento helado que corta hasta los huesos.", 4);
                    imprimirPocoAPoco("  Con un daño alto, el Dragón de Hielo desata su furia congelante sobre sus enemigos, dejándolos inmóviles y vulnerables.", 4);
                    imprimirPocoAPoco("  Aunque su salud es aceptable, su capacidad para dominar el campo de batalla con su control del hielo lo convierte en una amenaza formidable.", 4);
                    imprimirPocoAPoco("  Sus escamas brillan como el cristal bajo la luz, reflejando su naturaleza fría y despiadada.", 4);
                    imprimirPocoAPoco("  A medida que desciende en picada desde los cielos, su rugido retumba como una tormenta de nieve, sembrando el miedo en los corazones de sus adversarios.", 4);
                    imprimirPocoAPoco("  La combinación de su fuerza bruta y sus poderes helados asegura que pocos puedan sobrevivir a su ira.", 4);
                    imprimirPocoAPoco("  Los guerreros que se atrevan a enfrentarlo deben estar preparados para un desafío gélido y mortal.", 4);

                    imprimirInfoMonstruo(3);
                    break;
                case 5:
                    mostrarimagen("guardianhierro.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Guardián de Hierro:" + RESET, 7);
                    imprimirPocoAPoco("  El Guardián de Hierro es un imponente guerrero equipado con una armadura metálica, experto en defensa y protección.", 5);
                    imprimirPocoAPoco("  Forjado en las batallas más cruentas, su armadura brilla con un resplandor impenetrable, reflejando su fortaleza inquebrantable.", 4);
                    imprimirPocoAPoco("  Con un daño medio, su verdadero poder radica en su capacidad para absorber golpes y proteger a sus aliados del daño.", 4);
                    imprimirPocoAPoco("  Su salud alta le permite resistir en el campo de batalla por largos períodos, actuando como un baluarte contra cualquier amenaza.", 4);
                    imprimirPocoAPoco("  Cada movimiento calculado y cada golpe que recibe en su armadura metálica resuena con un eco de invulnerabilidad.", 4);
                    imprimirPocoAPoco("  El Guardián de Hierro no solo defiende con su cuerpo, sino que inspira a sus compañeros con su valor y resistencia.", 4);
                    imprimirPocoAPoco("  En el corazón de la batalla, se erige como una muralla indomable, desafiando a los enemigos a intentar atravesar su defensa impenetrable.", 4);

                    imprimirInfoMonstruo(4);
                    break;
                case 6:
                    mostrarimagen("angelguardian.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Ángel Guardián:" + RESET, 7);
                    imprimirPocoAPoco("  El Ángel Guardián es una figura celestial que ofrece protección divina y puede sanar a sus aliados.", 5);
                    imprimirPocoAPoco("  Descendiendo de los cielos, este ser radiante utiliza su poder sagrado para defender a los justos y curar heridas con un toque divino.", 4);
                    imprimirPocoAPoco("  Con un daño medio, su fuerza reside no solo en sus ataques, sino en su capacidad de mantener a su equipo en pie durante las batallas más difíciles.", 4);
                    imprimirPocoAPoco("  Su salud medianamente alta le permite resistir en combate, otorgando una presencia constante y protectora en el campo de batalla.", 4);
                    imprimirPocoAPoco("  La luz que emana de su ser inspira valor y esperanza en sus aliados, mientras que sus enemigos sienten la pureza de su poder celestial.", 4);
                    imprimirPocoAPoco("  La combinación de sus habilidades ofensivas y defensivas lo convierte en un pilar indispensable en cualquier equipo,", 4);
                    imprimirPocoAPoco("  capaz de cambiar el curso de la batalla con su intervención oportuna y su inquebrantable defensa.", 4);
                    imprimirInfoMonstruo(5);
                    break;
                case 7:
                    mostrarimagen("elfosanador.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Elfo Sanador:" + RESET, 7);
                    imprimirPocoAPoco("  El Elfo Sanador es un ser mágico especializado en curación rápida y eficiente.", 5);
                    imprimirPocoAPoco("  Con su conocimiento profundo de las artes curativas, es capaz de restaurar la salud de sus aliados en un abrir y cerrar de ojos.", 4);
                    imprimirPocoAPoco("  Aunque posee un daño sorprendentemente alto para un sanador, su salud es frágil,", 4);
                    imprimirPocoAPoco("  lo que lo convierte en un objetivo prioritario para los enemigos en el campo de batalla.", 4);
                    imprimirPocoAPoco("  Su habilidad para mantenerse a salvo y fuera del alcance de los ataques es vital para su supervivencia.", 4);
                    imprimirPocoAPoco("  Con su presencia, cualquier equipo de combate se ve fortalecido, ya que puede revertir los daños sufridos con rapidez.", 4);
                    imprimirPocoAPoco("  Sin embargo, su fragilidad requiere una protección constante por parte de sus compañeros,", 4);
                    imprimirPocoAPoco("  pues la pérdida del Elfo Sanador puede inclinar la balanza de la batalla en contra de su equipo.", 4);

                    imprimirInfoMonstruo(6);
                    break;
                case 8:
                    mostrarimagen("vampiro.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Vampiro Ancestral:" + RESET, 7);
                    imprimirPocoAPoco("  El Vampiro Ancestral es un ser nocturno que drena la vida de sus enemigos para fortalecerse.", 5);
                    imprimirPocoAPoco("  Con siglos de experiencia, este vampiro ha perfeccionado su habilidad para manipular las sombras y la noche.", 4);
                    imprimirPocoAPoco("  Una criatura con un daño medio y una salud alta, se destaca por su capacidad de absorber la vitalidad de sus víctimas,", 4);
                    imprimirPocoAPoco("  restaurando su propia salud con cada mordida.", 4);
                    imprimirPocoAPoco("  Su presencia en el campo de batalla es imponente, y sus enemigos a menudo caen presa del pánico al sentir su mirada penetrante.", 4);
                    imprimirPocoAPoco("  Además de su fuerza física, haciendo de él un oponente temido y respetado.", 4);
                    imprimirPocoAPoco("  Aquellos que se atrevan a enfrentarlo deben estar preparados para un combate agotador,", 4);
                    imprimirPocoAPoco("  pues cada golpe que recibe solo lo hace más fuerte.", 4);

                    imprimirInfoMonstruo(7);
                    break;
                case 9:
                    mostrarimagen("espectrosombrio.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Espectro Sombrío:" + RESET, 7);
                    imprimirPocoAPoco("  El Espectro Sombrío es un espíritu etéreo que se desliza entre las sombras, atacando con oscuridad pura.", 5);
                    imprimirPocoAPoco("  Aunque su daño es relativamente bajo y su salud es frágil,", 4);
                    imprimirPocoAPoco("  su agilidad es incomparable, permitiéndole evadir ataques con facilidad.", 4);
                    imprimirPocoAPoco("  Su presencia es apenas perceptible, y su velocidad le permite golpear y desaparecer rápidamente.", 4);
                    imprimirPocoAPoco("  Se dice que aquellos que lo enfrentan sienten un frío helado recorrer su espina dorsal,", 4);
                    imprimirPocoAPoco("  pues el Espectro Sombrío parece desvanecerse en el aire solo para reaparecer en otro lugar.", 4);
                    imprimirPocoAPoco("  Con una táctica de guerrilla, ataca y se oculta en las sombras, esperando el momento perfecto para volver a golpear.", 4);
                    imprimirPocoAPoco("  Aquellos que subestimen su capacidad de daño o su velocidad se encontrarán rápidamente sobrepasados.", 4);

                    imprimirInfoMonstruo(8);
                    break;
                case 10:
                    mostrarimagen("nigromante_oscuro.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Nigromante Oscuro:", 7);
                    imprimirPocoAPoco(RESET + "  El Nigromante Oscuro es un hechicero malévolo, maestro de las artes oscuras.", 5);
                    imprimirPocoAPoco("  Con su aura siniestra, lanza poderosos conjuros oscuros que aumentan su fuerza con cada turno que pasa.", 4);
                    imprimirPocoAPoco("  Sus ataques varían desde un daño bajo hasta un daño increíblemente alto,", 4);
                    imprimirPocoAPoco("  haciendo que sea una amenaza impredecible en el campo de batalla.", 4);
                    imprimirPocoAPoco("  Con una salud elevada, este hechicero oscuro puede soportar muchos ataques,", 4);
                    imprimirPocoAPoco("  lo que lo convierte en un adversario formidable y temido por todos.", 4);
                    imprimirPocoAPoco("  Aquellos que osen enfrentarlo deben estar preparados para una batalla larga y peligrosa,", 4);
                    imprimirPocoAPoco("  pues el Nigromante Oscuro se fortalece con el tiempo, convirtiéndose en una amenaza aún mayor.", 4);

                    imprimirInfoMonstruo(9);
                    break;
                case 11:
                    mostrarimagen("sombra_toxica.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Sombra Tóxica:", 7);
                    imprimirPocoAPoco(RESET + "  Sombra Tóxica, una entidad envuelta en misterio y letalidad.", 5);
                    imprimirPocoAPoco("  Esta enigmática criatura surge de las sombras con un propósito oscuro.", 4);
                    imprimirPocoAPoco("  Con un equilibrio perfecto entre daño medio y salud medio,", 4);
                    imprimirPocoAPoco("  posee la temida habilidad de envenenar a sus adversarios.", 4);
                    imprimirPocoAPoco("  Aquellos que la enfrenten, deben estar preparados para un desafío mortal.", 4);

                    imprimirInfoMonstruo(10);
                    break;
                case 12:
                    mostrarimagen("guerreroeterno.txt");
                    System.out.println("");
                    imprimirPocoAPoco(AMARILLO + "  Guerrero Eterno:", 7);
                    imprimirPocoAPoco(RESET + "  Un guerrero legendario forjado en las llamas del combate eterno.", 5);
                    imprimirPocoAPoco("  Esta formidable entidad encarna la resistencia y la fuerza bruta.", 4);
                    imprimirPocoAPoco("  Con un equilibrio perfecto entre poder ofensivo y defensivo,", 4);
                    imprimirPocoAPoco("  lucha incansablemente, con el alma de un verdadero campeón.", 4);
                    imprimirPocoAPoco("  Enfrentarlo es enfrentarse a la inmortalidad misma.", 4);


                    imprimirInfoMonstruo(11);
                    break;
                case 0:
                    Guia(scanner);
                    return;
                default:
                    imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                    InfoInvocaciones(scanner);
                    return;
            }
            System.out.println("");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            InfoInvocaciones(scanner);
        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);
            InfoInvocaciones(scanner);
        }
        InfoInvocaciones(scanner);
    }
    private static void imprimirInfoMonstruo(int index) {
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Atributos:", 7);
        imprimirPocoAPoco(RESET + "  * Nivel: " + NivelMonstruo[index], 7);
        imprimirPocoAPoco("  * Salud máxima: " + SaludMonstruo[index], 7);
        imprimirPocoAPoco("  * Daño máx: " + DañoMonstruo[index], 7);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadMoustro[index], 7);
        imprimirPocoAPoco("  * Curación a jugador: " + CuraciónMonstruo[index], 7);
        imprimirPocoAPoco("  * Probabilidad de fallar: " + (calcularProbabilidadFallo(index) * 100) + "%", 7);
    }
    private static void InfoEquipamiento(Scanner scanner) {
        borrarConsola(100);
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  ¿Qué te gustaría saber?", 5);
        imprimirPocoAPoco(VERDE + "  1. Información sobre el escudo mágico.", 4);
        imprimirPocoAPoco("  2. Información sobre el escudo físico.", 4);
        imprimirPocoAPoco("  3. Información sobre la armadura.", 4);
        imprimirPocoAPoco("  4. Información sobre la espada.", 4);
        imprimirPocoAPoco(CYAN + "  0. Volver a las opciones anteriores." + RESET, 4);

        String opcion = scanner.nextLine().trim();
        borrarConsola(100);
        System.out.println("");
        switch (opcion) {
            case "1":
                mostrarimagen("escudomagico.txt");

                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 7);
                imprimirPocoAPoco("  Este escudo absorbe el daño antes de afectar al jugador.", 7);
                imprimirPocoAPoco("  El Escudo Mágico es una poderosa defensa obtenible por la habilidad de guerrero o mediante el bastón del mago.", 7);

                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Encantamientos disponibles:"  + RESET, 7);
                    imprimirPocoAPoco("  *! El escudo mágico, no posee encantamientos a agregar.", 7);


                break;

            case "2":
                mostrarimagen("escudofisico.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Descripción:"  + RESET, 7);
                imprimirPocoAPoco("  Este escudo es ideal para protegerse de ataques físicos.", 7);
                imprimirPocoAPoco("  El Escudo Físico se puede conseguir en la tienda y divide a la mitad el daño recibido, restando esa mitad a su durabilidad.", 7);

                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Encantamientos disponibles:"  + RESET, 7);
                    imprimirPocoAPoco("  * Escudo: Encantamiento de espinas, daño al enemigo por daño al escudo: 60%", 7);
                    imprimirPocoAPoco("  * Escudo: Encantamiento de bloqueo, probabilidad de bloquear: 37%", 7);
                    imprimirPocoAPoco("  * Escudo: Encantamiento de resistencia, probabilidad de reducir daño: 46%, reducción: 30%", 7);
                    imprimirPocoAPoco("  * Escudo: Encantamiento de parálisis, probabilidad de paralizar enemigo: 33%", 7);

                break;

            case "3":
                mostrarimagen("armadura.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Descripción:"  + RESET, 7);
                imprimirPocoAPoco("  Esta armadura proporciona una capa adicional de protección, absorbiendo el daño antes de que afecte al jugador.", 7);
                imprimirPocoAPoco("  La Armadura se puede conseguir en la tienda y sirve como una segunda vida para el jugador, absorbiendo todo el daño recibido.", 7);

                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Encantamientos disponibles:"  + RESET, 7);
                    imprimirPocoAPoco("  * Armadura: Encantamiento de espinas, daño al enemigo por daño a la armadura: 30%", 7);
                    imprimirPocoAPoco("  * Armadura: Encantamiento de reparación, reparación del daño recibido: 0% - 100%", 7);
                    imprimirPocoAPoco("  * Armadura: Encantamiento de agilidad, agilidad adicional: 2", 7);
                    imprimirPocoAPoco("  * Armadura: Encantamiento de recarga, puntos de recarga: " + PuntosRecargaArmadura + "/2", 7);

                break;

            case "4":
                mostrarimagen("espada.txt");
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Descripción:"  + RESET, 7);
                imprimirPocoAPoco("  Esta espada permite al jugador aumentar el daño de los ataques contra sus enemigos.", 7);
                imprimirPocoAPoco("  La Espada se puede conseguir en la tienda y es un arma fundamental en combate. Cada ataque exitoso reduce su durabilidad en 1.", 7);

                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  Encantamientos disponibles:"  + RESET, 7);
                    imprimirPocoAPoco("  * Espada: Encantamiento crítico, activación: 60%, probabilidad de crítico: +20%, aumento de daño: +40% - +160%", 7);
                    imprimirPocoAPoco("  * Espada: Encantamiento de curación, probabilidad de curar: 50%, curación al jugador: 1 - daño de espada", 7);
                    imprimirPocoAPoco("  * Espada: Encantamiento de resistencia, puntos de resistencia: " + PuntosResistenciaEspada + "/4", 7);
                    imprimirPocoAPoco("  * Espada: Encantamiento de velocidad, probabilidad de atacar de nuevo: 50%", 7);

                break;

            case "0":
                Guia(scanner);
                break;
            default:
                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                scanner.nextLine();
                InfoEquipamiento(scanner);
                return;
        }
        System.out.println("");
        System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
        scanner.nextLine();
        InfoEquipamiento(scanner);
    }


    private static void inicio(Scanner scanner) {
        System.out.println("");
        imprimirPocoAPoco("  " + AMARILLO + NombreJugador + RESET + ", Te encuentras en la entrada del Bosque Encantado.", 20);
        imprimirPocoAPoco("  El aire se vuelve denso y pesado a medida que te adentras en el bosque.", 20);
        imprimirPocoAPoco("  Hay dos caminos dentro del bosque, cualquiera que escojas estará lleno de desafíos y recompensas.", 20);
        imprimirPocoAPoco(VERDE + "  1. Caminar por el sendero de la derecha.", 20);
        imprimirPocoAPoco("  2. Caminar por el sendero de la izquierda.", 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);

        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            System.out.println("");
            if (opcion == 1) {
                senderoDerecha(scanner);
            } else if (opcion == 2) {
                System.out.println("Desafortunadamente este camino aún no está disponible.");
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();
                System.out.println("  Redirigiendo...");
                imprimirPocoAPoco(" . . .", 400);
                senderoIzquierda(scanner); // Suponiendo que este método manejará el camino de la izquierda
            } else {
                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                inicio(scanner);
            }
        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);
            inicio(scanner);
        }
    }
//listo

    //---------1-SenderoDerecha--------------------------------------------------------
    private static void senderoDerecha(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Caminas por el sendero de la derecha.", 20);
        imprimirPocoAPoco("  ...", 300);
        imprimirPocoAPoco("  Ves muchos árboles y plantas en tu camino, nada interesante...", 20);
        imprimirPocoAPoco("  De pronto te topas con la cueva de una montaña.", 20);
        imprimirPocoAPoco("  Sientes la curiosidad de qué habrá dentro, o a dónde te llevará.", 20);
        imprimirPocoAPoco("  ...", 300);
        imprimirPocoAPoco(VERDE + "  1. Seguir caminando.", 20);
        imprimirPocoAPoco("  2. Explorar la cueva." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);

        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            System.out.println("");
            if (opcion == 1) {
                System.out.println("Desafortunadamente este camino aún no está disponible.");
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();
                System.out.println("  Redirigiendo...");
                imprimirPocoAPoco(" . . .", 400);
                senderoDerecha(scanner); // Aquí redirige de nuevo al sendero derecho
            } else if (opcion == 2) {
                explorarCueva(scanner);
            } else {
                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                senderoDerecha(scanner);
            }
        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);

            senderoDerecha(scanner);
        }
    }
    //listo

    //---------1-SenderoDerecha----1-SeguirCaminando---------------------------------------------------
    private static void seguirCaminandoDerecha(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Sigues caminando por el sendero de la derecha.", 20);
        imprimirPocoAPoco("  Llevas horas caminando, parece que pronto se volverá de noche.", 20);
        imprimirPocoAPoco(" . . .", 300);
        imprimirPocoAPoco(VERDE + "  1. Tomar un descanso.", 20);
        imprimirPocoAPoco("  2. Continuar sin descansar." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);

        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            if (opcion == 1) {
                imprimirPocoAPoco("  Tomas un descanso y un animal salvaje te ataca.", 25);
                Salud[0] -= 5;
                imprimirPocoAPoco(AMARILLO + "  Salud del jugador: " + Salud[0] + RESET, 15);
                imprimirPocoAPoco("  Caes cerca de unos arbustos y encuentras una espada de madera.", 20);
                imprimirPocoAPoco("  ...", 300);
                imprimirPocoAPoco("  No parece muy duradera, pero sin duda puede ayudarte un poco.", 20);
                imprimirPocoAPoco(VERDE + "  1. Tomar la espada.", 20);
                imprimirPocoAPoco("  2. Dejar la espada." + RESET, 20);
                imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
                int opcion2 = scanner.nextInt();

                if (opcion2 == 1) {
                    imprimirPocoAPoco("  Tomas la espada, ¡sin duda una gran elección!", 20);
                    Salud[3] += 4;
                    Daño[1] = (ClaseMago) ? Daño[1] + 4 : Daño[1] + 5;
                    imprimirPocoAPoco(MAGENTA + "  Has obtenido una espada, durabilidad: " + Salud[3] + RESET, 15);
                    imprimirPocoAPoco("  Pasas la noche tranquilo porque tienes con qué protegerte.", 20);
                } else if (opcion2 == 2) {
                    imprimirPocoAPoco("  Dejas la espada donde la encontraste y continúas descansando.", 15);
                    imprimirPocoAPoco("  Llega la noche y no puedes dormir a gusto.", 15);
                } else {
                    imprimirPocoAPoco(MAGENTA + "  Te crees más listo y tomas otra opción." + RESET, 40);
                    imprimirPocoAPoco("  ...", 200);
                    imprimirPocoAPoco("  Tu decisión esta vez es permitida", 20);
                    imprimirPocoAPoco("  Cuando se vuelve de noche...", 20);
                    imprimirPocoAPoco("  Decides romper la espada de madera y hacer una fogata con ella.", 20);
                    imprimirPocoAPoco("  Pasas la noche tranquilo y duermes a gusto.", 20);
                    imprimirPocoAPoco("  ...", 200);
                    Salud[0] += 5;
                    imprimirPocoAPoco(AMARILLO + "  Salud del jugador: " + Salud[0] + RESET, 20);
                }

                tomarDescanso(scanner);
            } else if (opcion == 2) {
                CaminandoDerechaSinDescansar(scanner);
            } else {
                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 15);
                seguirCaminandoDerecha(scanner);
            }
        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);

            seguirCaminandoDerecha(scanner);
        }
    }


    //---------1-SenderoDerecha----1-SeguirCaminando----1-Descanso----------------------------------------------
    private static void tomarDescanso(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  ...", 200);
        imprimirPocoAPoco("  Después del descanso decides seguir caminando.", 20);
        imprimirPocoAPoco("  Pasan las horas y continuas caminando.", 20);
        CaminandoDerechaSinDescansar(scanner); // Asumiendo que este método maneja el siguiente paso después del descanso
    }


    //---------1-SenderoDerecha----1-SeguirCaminando---2-SinDescansar/1-sin descanso-----------------------------
    private static void CaminandoDerechaSinDescansar(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        imprimirPocoAPoco(" ", 1500);
        imprimirPocoAPoco("  Te encuentras con un enorme árbol grueso y frondoso.", 20);
        imprimirPocoAPoco("  Detrás del árbol parece haber una pequeña fuente misteriosa.", 20);
        imprimirPocoAPoco("  Te sientes tentado a investigar, pero eres inteligente y sabes tomar la mejor decisión.", 20);
        imprimirPocoAPoco(VERDE + "  1. Inspeccionar la fuente.", 20);
        imprimirPocoAPoco("  2. Ignorar la fuente y seguir caminando." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 20);
        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea


            if (opcion == 1) {
                imprimirPocoAPoco("  Decides inspeccionar la fuente, pero...", 20);
                System.out.println("");
                NivelEnemigo = "Nivel-1";
                SimularEncuentro();
                InspeccionarFuente(scanner);
            }
            else if (opcion == 2) {
                imprimirPocoAPoco("  No hay nada aquí todavía...", 20);
            }
            else {
                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 20);
                CaminandoDerechaSinDescansar(scanner);
            }
        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);

            CaminandoDerechaSinDescansar(scanner);
        }

    } //Falta continuar

    //-----1-SenderoDerecha----1-SeguirCaminando---2-SinDescansar--2-InspeccionarFuente--------------------------------
    private static void InspeccionarFuente(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Tal parece que la fuente era solo una trampa.", 20);
        imprimirPocoAPoco("  Sin embargo encuentras unas cuantas gemas.", 20);
        imprimirPocoAPoco("  Tomas las gemas ya que es tu recompensa por las molestias.", 20);
        System.out.println("");
        int gemasobtenidas = (int) (Math.random() * 8) + 3;
        Gemas += gemasobtenidas;
        imprimirPocoAPoco(AMARILLO + "  Has obtenido Gemas: +" + gemasobtenidas + RESET, 20);
        System.out.println("");


        imprimirPocoAPoco(VERDE + "  1. Falta continuar", 20);
        imprimirPocoAPoco("  2. " + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();

    }//falta continuar




    //----------1-SenderoDerecha---2-Explorar cueva--------------------------------------------------------------------
    private static void explorarCueva(Scanner scanner) {
        ContadorRecargaVaritasViajando();

        System.out.println("  Cargando...");
        imprimirPocoAPoco(" . . .", 300);
        borrarConsola(10000);
        audioPlayer.stop();

        audioPlayer.play("cueva.wav");
        System.out.println("");
        imprimirPocoAPoco("  Entras en la cueva y escuchas un ruido extraño.", 20);
        imprimirPocoAPoco("  Por suerte tienes una antorcha contigo, puedes usarla para ver en la oscuridad.", 20);
        imprimirPocoAPoco(VERDE + "  1. Encender una antorcha.", 20);
        imprimirPocoAPoco("  2. Avanzar en la oscuridad." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 20);
        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            if (opcion == 1) {
                imprimirPocoAPoco(" ...", 300);
                System.out.println("");
                Cantidades[4] -= 1; //antorcha
                Cantidades[3] += 1; //mapa
                imprimirPocoAPoco("  Enciendes la antorcha, pero no ves nada inusual a tu alrededor.", 20);
                imprimirPocoAPoco(AMARILLO + "  * " + Objetos[4] + ": " + Cantidades[4] + RESET, 20);
                imprimirPocoAPoco("  Recorres un buen tramo dentro de la cueva y empiezas a desesperarte.", 20);
                imprimirPocoAPoco(" ...", 300);
                imprimirPocoAPoco("  Sientes una presencia, pero no logras ver nada.", 20);
                imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 15);
                scanner.nextLine(); // Espera a que el usuario presione Enter

                imprimirPocoAPoco(" ...", 300);
                imprimirPocoAPoco("  Dentro de la cueva, tú, " + AMARILLO + NombreJugador + RESET + " descubres un cofre antiguo cubierto de polvo. ", 20);
                imprimirPocoAPoco("  Al abrirlo, encuentras un mapa del tesoro.", 20);

                imprimirPocoAPoco("  Aunque el mapa está marcado con símbolos y caminos desconocidos,", 20);
                imprimirPocoAPoco("  " + AMARILLO + NombreJugador + RESET + ", sientes una fuerte curiosidad por descubrir su secreto.", 20);
                imprimirPocoAPoco("  Decides llevar el mapa contigo, ", 20);
                imprimirPocoAPoco(MAGENTA + "  * " + Objetos[3] + ": " + Cantidades[3] + RESET, 20);
                imprimirPocoAPoco("  También encuentras unas gemas y te las llevas.", 20);
                Gemas += 53;
                imprimirPocoAPoco(AMARILLO + "  * Gemas: " + Gemas + RESET, 20);
                System.out.println("");
                imprimirPocoAPoco("  Mientra revisas el mapa, una criatura que asecha en la oscuridad,", 20);
                imprimirPocoAPoco("  decide atacarte mientras estás distraído, pero consigues esquivarlo.", 20);
                imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 15);
                scanner.nextLine(); // Espera a que el usuario presione Enter

                System.out.println("");

                System.out.println("  Cargando...");
                imprimirPocoAPoco(" . . .", 300);
                borrarConsola(10000);
                NivelEnemigo = "Nivel-1";
                SimularEncuentro();
                SalirDeCueva(scanner);

            }
            else if (opcion == 2) {
                imprimirPocoAPoco("  ...", 300);
                System.out.println("");
                imprimirPocoAPoco(ROJO + "  Avanzas en la oscuridad y te pierdes en la cueva. Fin del juego." + RESET, 20);
                imprimirPocoAPoco("  . . . . . . .", 500);
                System.exit(0);
            }
            else {
                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 15);
                explorarCueva(scanner);
            }

        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);

            explorarCueva(scanner);
        }
    }//listo

    //----------1-SenderoDerecha---2-Explorar cueva----1-Encender antorcha---------------------------------------------
    private static void SalirDeCueva(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        audioPlayer.stop();

        audioPlayer.play("aves.wav");
        System.out.println("");
        imprimirPocoAPoco("  Tras derrotar al enemigo, avanzas por la cueva en busca de una salida.", 20);
        imprimirPocoAPoco("  ...", 300);
        imprimirPocoAPoco("  Afortunadamente, logras encontrar una salida y supiste mantener la calma en todo momento.", 20);
        imprimirPocoAPoco("  Lo negativo de esta situación es que no sabes exactamente dónde estás.", 20);
        imprimirPocoAPoco("  Observas a tu alrededor, pero solo consigues ver árboles.", 20);
        imprimirPocoAPoco("  ...", 300);
        imprimirPocoAPoco("  Caminas un poco y sientes mucha hambre. Por suerte, ves unos arbustos de bayas.", 20);
        imprimirPocoAPoco(VERDE + "  1. Comer las bayas.", 20);
        imprimirPocoAPoco("  2. No comer las bayas.", 20);
        imprimirPocoAPoco("  3. Buscar otra cosa." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las tres opciones anteriores." + RESET, 15);

        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            if (opcion == 1) {
                imprimirPocoAPoco("  ...", 300);
                imprimirPocoAPoco("  Comes las bayas, afortunadamente no son venenosas.", 20);
                Salud[0] += 10;
                ComerValla = true;
                if (Salud[0] > SaludMax[0]) {
                    Salud[0] = SaludMax[0];
                }
                imprimirPocoAPoco(AMARILLO + "  Salud del jugador: " + Salud[0] + RESET, 15);
                EncontrarComerciante(scanner);

            } else if (opcion == 2) {
                imprimirPocoAPoco("  ...", 300);
                imprimirPocoAPoco("  Decides no comer y continuar caminando.", 20);
                EncontrarComerciante(scanner);

            } else if (opcion == 3) {
                imprimirPocoAPoco("  ...", 300);
                imprimirPocoAPoco("  Después de buscar un rato, no encuentras nada más.", 20);
                EncontrarComerciante(scanner);

            } else {
                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                SalirDeCueva(scanner);
            }

        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);

            SalirDeCueva(scanner);
        }
    }


    //----------1-SenderoDerecha---2-Explorar cueva----1-Encender antorcha---1-Comer/2-No comer/3-Buscar--------------------------------------
    private static void EncontrarComerciante(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  A lo lejos consigues ver a una persona y te apresuras a acercarte.", 20);
        imprimirPocoAPoco("  ...", 300);
        imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
        imprimirPocoAPoco("  Hola, buen señor. Disculpe que lo moleste.", 20);
        imprimirPocoAPoco("  ¿Me podría indicar el camino para salir del bosque encantado?", 20);
        System.out.println("");
        imprimirPocoAPoco("  .....", 300);
        System.out.println("");
        imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
        imprimirPocoAPoco("  ¡Por supuesto, joven!", 20);
        imprimirPocoAPoco("  Pero antes, ¿te interesaría comprar algo de mi mercancía?", 20);
        imprimirPocoAPoco("  Te aseguro que puedes obtener algo de utilidad.", 20);
        imprimirPocoAPoco("  ...", 300);
        imprimirPocoAPoco(VERDE + "  1. Echar un vistazo a la mercancía.", 20);
        imprimirPocoAPoco("  2. Negarse a la petición del comerciante." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);

        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            if (opcion == 1) {
                imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
                imprimirPocoAPoco("  ¡Excelente decisión!", 20);
                imprimirPocoAPoco("  Como eres una persona agradable, te daré un descuento del 10% en todos mis productos." + RESET, 20);
                Descuento = 0.1;
                imprimirPocoAPoco("  " + MAGENTA + "Descuento: " + (int) (Descuento * 100) + "%" + RESET, 40);

                comerciante();
                CharlarComercianteBosque(scanner);

            } else if (opcion == 2) {
                imprimirPocoAPoco("  ¡Es una pena!..." + RESET, 20);
                CharlarComercianteBosque(scanner);

            } else {
                imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                EncontrarComerciante(scanner);
            }

        } else {
            scanner.nextLine();  // Consumir la entrada no numérica
            imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 20);
            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 400);

            EncontrarComerciante(scanner);
        }
    }


    //--1--2----1-Encender antorcha---1-Comer/2-No comer/3-Buscar----1-Comprar/2-Negarse--------------------------------
    private static void CharlarComercianteBosque(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        System.out.println("  Cargando...");
        imprimirPocoAPoco(" . . .", 300);
        borrarConsola(1000);
        System.out.println("");
        imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
        imprimirPocoAPoco("  Bueno, joven aventurero, existen varios caminos para salir de este bosque.", 20);
        imprimirPocoAPoco("  Hay tres caminos: uno te lleva a una aldea cercana, otro al reino Celeste, y otro al valle de los duendes.", 20);
        imprimirPocoAPoco("  ¿A qué destino quieres dirigirte?", 20);
        imprimirPocoAPoco("  ...", 300);
        imprimirPocoAPoco(VERDE + "  1. La aldea cercana.", 20);
        imprimirPocoAPoco("  2. El reino Celeste.", 20);
        imprimirPocoAPoco("  3. El valle de los duendes." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las tres opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        System.out.println("");

        if (opcion == 1) {
            imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
            imprimirPocoAPoco("  Para llegar a la aldea más cercana, debes seguir el camino de los arbustos de bayas." + RESET, 20);
            System.out.println("");
            imprimirPocoAPoco("  Después de hablar con el comerciante, emprendes tu viaje hacia la aldea más cercana.", 20);
            imprimirPocoAPoco("  A lo largo del camino, te encuentras con un enemigo.", 20);
            imprimirPocoAPoco("  ¿Te crees capaz de derrotarlo?" + VERDE + " (1. Sí) (2. No)" + RESET, 20);
            imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
            int opcion2 = scanner.nextInt();
            if (opcion2 == 1) {
                NivelEnemigo = "Nivel-1";
                SimularEncuentro();
                System.out.println("  Cargando...");
                imprimirPocoAPoco(" . . .", 300);
                borrarConsola(1000);
                int GemasRandom = (int) (Math.random() * 10) + 15;
                Gemas += GemasRandom;
                imprimirPocoAPoco("  Tras derrotar al enemigo, consigues unas cuantas gemas.", 20);
                imprimirPocoAPoco(MAGENTA + "  +" + GemasRandom + " gemas." + RESET, 20);
                imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20);
            } else {
                imprimirPocoAPoco("  Al no sentirte capaz, decides rodear al enemigo con mucha cautela.", 20);
            }
            System.out.println("");
            imprimirPocoAPoco("  Sigues caminando, ya que la aldea está muy cerca.", 20);
            System.out.println(AZUL + "  Presiona Enter para continuar..." + RESET);
            scanner.nextLine();scanner.nextLine();
            AldeaCercanaBosque(scanner);

        } else if (opcion == 2) {
            imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
            imprimirPocoAPoco("  Para llegar al reino Celeste, debes rodear la montaña." + RESET, 20);
            System.out.println("");
            imprimirPocoAPoco("  Después de hablar con el comerciante, emprendes tu viaje hacia el reino Celeste.", 20);
            imprimirPocoAPoco("  A lo largo del camino, te encuentras con un enemigo.", 20);
            imprimirPocoAPoco("  ¿Te crees capaz de derrotarlo?" + VERDE + " (1. Sí) (2. No)" + RESET, 20);
            imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
            int opcion2 = scanner.nextInt();
            if (opcion2 == 1) {
                NivelEnemigo = "Nivel-1";
                SimularEncuentro();
                System.out.println("  Cargando...");
                imprimirPocoAPoco(" . . .", 300);
                borrarConsola(1000);
                int GemasRandom = (int) (Math.random() * 10) + 15;
                Gemas += GemasRandom;
                System.out.println("");
                imprimirPocoAPoco("  Tras derrotar al enemigo, consigues unas cuantas gemas.", 20);
                imprimirPocoAPoco(MAGENTA + "  +" + GemasRandom + " gemas." + RESET, 20);
                imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20);
            } else {
                imprimirPocoAPoco("  Al no sentirte capaz, decides rodear al enemigo con mucha cautela.", 20);
            }
            System.out.println("");
            System.out.println("Desafortunadamente, este camino aún no está disponible.");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            System.out.println("  Redirigiendo...");
            imprimirPocoAPoco(" . . .", 400);
            AldeaCercanaBosque(scanner); // Eliminar más adelante

        } else if (opcion == 3) {
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
            imprimirPocoAPoco("  Para llegar al valle de los duendes, debes caminar recto hacia tu izquierda." + RESET, 20);
            System.out.println("");
            imprimirPocoAPoco("  Después de hablar con el comerciante, emprendes tu viaje hacia el valle de los duendes.", 20);
            imprimirPocoAPoco("  A lo largo del camino, te encuentras con un enemigo.", 20);
            imprimirPocoAPoco("  ¿Te crees capaz de derrotarlo?" + VERDE + " (1. Sí) (2. No)" + RESET, 20);
            imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
            int opcion2 = scanner.nextInt();
            if (opcion2 == 1) {
                NivelEnemigo = "Nivel-1";
                SimularEncuentro();
                System.out.println("  Cargando...");
                imprimirPocoAPoco(" . . .", 300);
                borrarConsola(1000);
                int GemasRandom = (int) (Math.random() * 10) + 15;
                Gemas += GemasRandom;
                System.out.println("");
                imprimirPocoAPoco("  Tras derrotar al enemigo, consigues unas cuantas gemas.", 20);
                imprimirPocoAPoco(MAGENTA + "  +" + GemasRandom + " gemas." + RESET, 20);
                imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20);
            } else {
                imprimirPocoAPoco("  Al no sentirte capaz, decides rodear al enemigo con mucha cautela.", 20);
            }

            ValleDuende(scanner);

        } else {
            imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
            CharlarComercianteBosque(scanner);
        }
    }
//Falta continuar ReionoCeleste
//aqui me quede




    //--1--2----1-Encender antorcha---1-Comer/2-No comer/3-Buscar----1-Comprar/2-Negarse----3-camino valle duende---------------------------
    private static void ValleDuende(Scanner scanner) {

        System.out.println("  Cargando...");
        imprimirPocoAPoco(" . . .", 300);
        borrarConsola(10000);
        audioPlayer.stop();
        audioPlayer.play("pandilla.wav");
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Te adentras en el Valle de los Duendes, un lugar místico y lleno de magia.", 20);
        imprimirPocoAPoco("  A medida que avanzas, te das cuenta de que los duendes no son tan amigables como pensabas.", 20);
        imprimirPocoAPoco("  Sus ojos brillan con una luz maliciosa, y empiezas a sentirte incómodo.", 20);
        System.out.println("");
        imprimirPocoAPoco("  ...", 300);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  ¡Este lugar no parece seguro!", 20);
        imprimirPocoAPoco(VERDE + "  1. Regresar y dirigirte a la aldea cercana", 20);
        imprimirPocoAPoco("  2. Regresar y dirigirte al Reino Celeste." + RESET, 20);
        imprimirPocoAPoco(AMARILLO + "  3. Continuar caminando." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las tres opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        System.out.println("");

        if (opcion == 1) {
            imprimirPocoAPoco("  Mientras caminas, te sientes aliviado de haber escapado de una posible trampa.", 20);
            imprimirPocoAPoco("  Decides regresar y dirigirte a la aldea cercana.", 20);
            AldeaCercanaBosque(scanner);
        } else if (opcion == 2) {
            imprimirPocoAPoco("  Mientras caminas, te sientes aliviado de haber escapado de una posible trampa.", 20);
            imprimirPocoAPoco("  Decides dirigirte al Reino Celeste.", 20);
            System.out.println("Desafortunadamente, este camino aún no está disponible.");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            System.out.println("  Redirigiendo...");
            imprimirPocoAPoco(" . . .", 400);
            AldeaCercanaBosque(scanner); // Eliminar más adelante
        } else {
            imprimirPocoAPoco("  Decides seguir caminando.", 20);
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco("  A medio camino, una pandilla de duendes te rodea.", 20);

            ValleDuendeMusica = true;
            for (int i = 1; i < 3; i++) {
                imprimirPocoAPoco("  Te enfrentas al enemigo #" + i + " de 3.", 20);
                NivelEnemigo = "EspecificoDuende";
                SimularEncuentro();
            }
            ValleDuendeMusica = false;

            System.out.println("  Cargando...");
            imprimirPocoAPoco(" . . .", 300);
            borrarConsola(1000);
            int GemasRandom = (int) (Math.random() * 40) + 25;
            Gemas += GemasRandom;
            System.out.println("");
            imprimirPocoAPoco("  Tras derrotar a los duendes, consigues unas cuantas gemas.", 20);
            imprimirPocoAPoco(MAGENTA + "  +" + GemasRandom + " gemas." + RESET, 20);
            imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20);

            audioPlayer.stop();
            audioPlayer.setRepeat(true);
            audioPlayer.play("aves.wav");

            int CaminoRandom = (int) (Math.random() * 2) + 1;
            if (CaminoRandom == 1) {
                System.out.println("");
                imprimirPocoAPoco("  Después de pelear contra la pandilla de duendes,", 20);
                imprimirPocoAPoco("  corres hacia el bosque, sin tiempo para tomar una decisión.", 20);
                imprimirPocoAPoco("  Corres hacia la aldea cercana sin pensarlo.", 20);
                AldeaCercanaBosque(scanner);
            } else {
                System.out.println("");
                imprimirPocoAPoco("  Después de pelear contra la pandilla de duendes,", 20);
                imprimirPocoAPoco("  corres hacia el bosque, sin tiempo para tomar una decisión.", 20);
                imprimirPocoAPoco("  Corres hacia el Reino Celeste sin pensarlo.", 20);
                System.out.println("");
                System.out.println("Desafortunadamente, este camino aún no está disponible.");
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();
                System.out.println("  Redirigiendo...");
                imprimirPocoAPoco(" . . .", 400);
                AldeaCercanaBosque(scanner); // Eliminar después
            }
        }
    }
//falta

    //--1--2-1-1----1-Comprar/2-Negarse---1- Aldea cercana - (3-camino valle duende)-------------------------------------------
    private static void AldeaCercanaBosque(Scanner scanner) {
        ContadorRecargaVaritasViajando();

        System.out.println("  Cargando...");
        imprimirPocoAPoco(" . . .", 300);
        borrarConsola(10000);
        audioPlayer.stop();
        audioPlayer.play("medieval.wav");
        System.out.println("");
        imprimirPocoAPoco("  Tras un largo y agotador camino, finalmente llegas a una pequeña aldea.", 20);
        imprimirPocoAPoco("  La aldea está tranquila, con los aldeanos ocupados en sus tareas diarias.", 20);
        imprimirPocoAPoco("  Decides descansar en la taberna local y revisar el mapa del tesoro que encontraste en la cueva.", 20);

        if (Salud[0] == (SaludMax[0] / 2)) {
            Salud[0] += 25;
            System.out.println(MAGENTA + "  Descansando...");
            imprimirPocoAPoco("  . . . . . . . . . . . . . . . .", 300);
            imprimirPocoAPoco(AMARILLO + "  Salud: " + Salud[0] + RESET, 20);
        }

        System.out.println("");
        imprimirPocoAPoco("  Dentro de la taberna, los aldeanos te observan con curiosidad.", 20);
        imprimirPocoAPoco("  Ninguno parece reconocer el mapa hasta que un anciano se acerca a ti.", 20);
        System.out.println("");
        imprimirPocoAPoco("  ...", 300);
        System.out.println("");
        imprimirPocoAPoco("  \"Ese mapa es antiguo y está maldito\", dice el anciano, llamado " + AMARILLO + "Eamon." + RESET, 20);
        imprimirPocoAPoco("  \"Guía a un tesoro inmenso, pero todos los que lo han buscado han sucumbido a su maldición\".", 20);
        imprimirPocoAPoco("  Te ves intrigado ante las palabras de " + AMARILLO + "Eamon." + RESET, 20);
        System.out.println("");
        imprimirPocoAPoco(VERDE + "  1. Preguntar a Eamon más sobre la maldición.", 20);
        imprimirPocoAPoco("  2. Salir de la taberna y buscar más pistas en la aldea." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        System.out.println("");

        if (opcion == 1) {
            imprimirPocoAPoco("  Decides preguntar a Eamon más sobre la maldición del tesoro.", 20);
            System.out.println("");
            imprimirPocoAPoco(AMARILLO + "  Eamon" + RESET + " te cuenta historias de aventureros que perdieron su vida buscando el tesoro.", 20);
            imprimirPocoAPoco("  Te ayuda a descifrar el mapa y te dice el paradero de dicho tesoro.", 20);
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco("  Tal parece que el tesoro se encuentra en medio de una isla.", 20);
            imprimirPocoAPoco("  Llamada en los tiempos antiguos como \"Monte Negro\".", 20);
            imprimirPocoAPoco("  Esto por ser un lugar cubierto de ceniza y carbón debido a sus frecuentes actividades volcánicas,", 20);
            imprimirPocoAPoco("  y criaturas capaces de vivir en tales condiciones, al igual que cierta variedad de flora.", 20);
            System.out.println("");
            System.out.println(AZUL + "  Presiona Enter para continuar" + RESET);
            scanner.nextLine();scanner.nextLine();
            System.out.println("");
            imprimirPocoAPoco("  Dado que se hace de noche, decides pasar la noche ahí antes de iniciar tu viaje.", 20);
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco("  Sales de la taberna y decides seguir las indicaciones del mapa.", 20);
            imprimirPocoAPoco(" ", 500);
            System.out.println(AZUL + "  Presiona Enter para continuar..." + RESET);
            scanner.nextLine();
            AldeaSeguirPista(scanner);

        } else if (opcion == 2) {
            imprimirPocoAPoco("  Decides salir de la taberna y buscar más pistas en la aldea.", 20);
            imprimirPocoAPoco("  Hablas con varios aldeanos, pero nadie sabe nada sobre el mapa.", 20);
            imprimirPocoAPoco("  Finalmente, decides volver a la taberna y descifrar el mapa por tu cuenta.", 20);
            System.out.println("");
            System.out.println(MAGENTA + "  Descifrando...");
            imprimirPocoAPoco("  . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .", 400);
            System.out.println(MAGENTA + "  Descifrado completado." + AZUL + " (Enter para continuar)" + RESET);
            scanner.nextLine();
            System.out.println("");
            imprimirPocoAPoco("  Pasa lo que resta del día y un nuevo amanecer aparece desde el horizonte.", 20);
            imprimirPocoAPoco("  Aunque no consigues descifrarlo por completo, es lo suficiente como para saber a dónde dirigirte.", 20);
            imprimirPocoAPoco(" ", 500);
            System.out.println(AZUL + "  Presiona Enter para continuar..." + RESET);
            scanner.nextLine();
            AldeaSeguirPista(scanner);

        } else {
            imprimirPocoAPoco(MAGENTA + "  Opción inválida. Intenta de nuevo." + RESET, 10);
            AldeaCercanaBosque(scanner);
        }
    }


    //-1--2-1-1-1/2--1- Aldea cercana - (3-camino valle duende)--1-preguntar/2-buscar pista----------------
    private static void AldeaSeguirPista(Scanner scanner) {
        System.out.println("  Cargando...");
        imprimirPocoAPoco(" . . .", 300);
        borrarConsola(1000);
        ContadorRecargaVaritasViajando();
        System.out.println("");

        imprimirPocoAPoco("  Para seguir la pista del mapa,", 20);
        imprimirPocoAPoco("  tú, " + AMARILLO + NombreJugador + RESET + ", buscas un lugar en la aldea", 20);
        imprimirPocoAPoco("  donde puedas conseguir un mapa.", 20);
        System.out.println("");
        imprimirPocoAPoco(" . . .", 300);
        System.out.println("");
        imprimirPocoAPoco("  Encuentras una biblioteca en la aldea y decides buscar ahí.", 20);
        imprimirPocoAPoco("  Tras unas horas buscando, encuentras un mapa en un libro antiguo.", 20);
        imprimirPocoAPoco("  Gracias al mapa, sabes que debes dirigirte a otro pueblo llamado \"Vernum\".", 20);
        System.out.println("");
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar" + RESET, 10);
        scanner.nextLine();
        SalirAldea(scanner);
    }


    //-1--2-1-1-1/2--1- Aldea cercana - (3-camino valle duende)--1-preguntar/2-buscar pista----------------
    private static void SalirAldea(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Sales de la aldea y te diriges a \"Vernum\".", 20);
        imprimirPocoAPoco("  Puedes decidir si ir por el bosque encantado o tomar otro camino.", 20);
        imprimirPocoAPoco(VERDE + "  1. Ir por el bosque encantado.", 20);
        imprimirPocoAPoco("  2. Tomar otro camino." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        System.out.println("");
        if (opcion == 1) {
            imprimirPocoAPoco("  Te adentras de nuevo en el bosque.", 20);
            System.out.println(AZUL + "  Presiona Enter para continuar..." + RESET);
            scanner.nextLine();
            VolverBosqueAldea(scanner);
        } else if (opcion == 2) {
            imprimirPocoAPoco("  Tomas el camino que se encuentra del otro lado de la aldea.", 20);
            System.out.println(AZUL + "  Presiona Enter para continuar..." + RESET);
            scanner.nextLine();
            CaminoTrasAldea(scanner);
        } else {
            imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 15);
            SalirAldea(scanner);
        }
    }



    //--1-preguntar/2-buscar pista--1-VolverBosque--------------------------------------
    private static void VolverBosqueAldea(Scanner scanner) {
        audioPlayer.stop();
        audioPlayer.play("aves.wav");
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Sales de la aldea y te diriges a \"Vernum\" a través del bosque encantado.", 20);
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");
        imprimirPocoAPoco("  Llevas un rato dentro del bosque, pero gracias al mapa te sientes seguro del camino.", 20);
        imprimirPocoAPoco("  Parece que pronto anochecerá.", 20);
        imprimirPocoAPoco(VERDE + "  1. Buscar dónde pasar la noche.", 20);
        imprimirPocoAPoco(AMARILLO + "  2. Seguir caminando.", 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");

        if (opcion == 1) {
            imprimirPocoAPoco("  Encuentras un lugar aparentemente seguro para pasar la noche.", 20);
            System.out.println("");
            if (ClaseGuerrero) {
                imprimirPocoAPoco("  Consigues un poco de madera, palos y ramas e intentas hacer una fogata.", 20);
                if (Cantidades[4] > 0) { // Cantidades[4] se refiere a la cantidad de antorchas
                    imprimirPocoAPoco("  Parece que tienes una antorcha en tu inventario que puede ayudarte a encender la fogata.", 20);
                    imprimirPocoAPoco(VERDE + "  (1. Usar antorcha)" + AMARILLO + " (2. No usar)" + RESET, 20);
                    imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
                    int opcion2 = scanner.nextInt();
                    System.out.println("");

                    if (opcion2 == 1) {
                        Cantidades[4] -= 1; // Resta una antorcha del inventario
                        imprimirPocoAPoco("  Utilizas la antorcha y consigues hacer una fogata con ella.", 20);
                        imprimirPocoAPoco(AMARILLO + "  Antorchas restantes: " + Cantidades[4] + RESET, 20);
                        imprimirPocoAPoco("  Pasas la noche protegido por la luz de la fogata.", 20);
                    } else {
                        imprimirPocoAPoco("  Decides no usar la antorcha e intentar encenderla tú mismo.", 20);
                        System.out.println("  Encendiendo fogata...");
                        imprimirPocoAPoco("  . . . . . . . . . . . . . . . .", 400);
                        System.out.println("");
                        int fogatarandom = (int) (Math.random() * 2) + 1;

                        if (fogatarandom == 2) {
                            imprimirPocoAPoco("  Consigues encender la fogata.", 20);
                            imprimirPocoAPoco("  Pasas la noche protegido por la luz de la fogata.", 20);
                        } else {
                            imprimirPocoAPoco("  Desafortunadamente no consigues encender la fogata y te das por vencido.", 20);
                            int ataquerandom = (int) (Math.random() * 10) + 1;
                            imprimirPocoAPoco("  Posibilidad de ser atacado: 40%", 20);
                            imprimirPocoAPoco("  . . .", 400);

                            if (ataquerandom <= 4) {
                                NivelEnemigo = "Nivel-1";
                                SimularEncuentro(); // SimularEncuentro debe estar definida en algún otro lugar del código
                                audioPlayer.stop();
                                audioPlayer.setRepeat(true);
                                audioPlayer.play("aves.wav");
                                System.out.println("");
                                imprimirPocoAPoco("  Sobrevives al ataque y pasas la noche.", 20);
                                int gemasrandom = (int) (Math.random() * 20) + 1;
                                Gemas += gemasrandom;
                                imprimirPocoAPoco(AMARILLO + "  Obtuviste +" + gemasrandom + " gemas." + RESET, 20);
                                imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20); // Corregido para mostrar el total de gemas
                                System.out.println("");
                                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                                scanner.nextLine();scanner.nextLine();
                                BosqueDiaAvanzar(scanner); // BosqueDiaAvanzar debe estar definida en algún otro lugar del código
                            } else {
                                imprimirPocoAPoco("  Pasa la noche y afortunadamente no fuiste atacado.", 20);
                                System.out.println();
                                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                                scanner.nextLine();scanner.nextLine();
                                BosqueDiaAvanzar(scanner);
                            }
                        }
                    }
                } else {
                    imprimirPocoAPoco("  Intentas encender una fogata para pasar la noche.", 20);
                    System.out.println("  Encendiendo fogata...");
                    imprimirPocoAPoco("  . . . . . . . . . . . . . . . .", 400);
                    System.out.println("");
                    int fogatarandom = (int) (Math.random() * 2) + 1;

                    if (fogatarandom == 2) {
                        imprimirPocoAPoco("  Consigues encender la fogata.", 20);
                        imprimirPocoAPoco("  Pasas la noche protegido por la luz de la fogata.", 20);
                    } else {
                        imprimirPocoAPoco("  Desafortunadamente no consigues encender la fogata y te das por vencido.", 20);
                        int ataquerandom = (int) (Math.random() * 10) + 1;
                        imprimirPocoAPoco("  Posibilidad de ser atacado: 50%", 20);

                        if (ataquerandom <= 5) {
                            imprimirPocoAPoco("  . . .", 400);
                            NivelEnemigo = "Nivel-1";
                            SimularEncuentro();
                            audioPlayer.stop();
                            audioPlayer.setRepeat(true);
                            audioPlayer.play("aves.wav");
                            System.out.println("");
                            imprimirPocoAPoco("  Sobrevives al ataque y pasas la noche.", 20);
                            int gemasrandom = (int) (Math.random() * 20) + 1;
                            Gemas += gemasrandom;
                            imprimirPocoAPoco(AMARILLO + "  Obtuviste +" + gemasrandom + " gemas." + RESET, 20);
                            imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20); // Corregido para mostrar el total de gemas
                            System.out.println("");
                            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                            scanner.nextLine();scanner.nextLine();
                            BosqueDiaAvanzar(scanner);
                        } else {
                            imprimirPocoAPoco("  . . .", 400);
                            imprimirPocoAPoco("  Pasa la noche y afortunadamente no fuiste atacado.", 20);
                            System.out.println("");
                            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                            scanner.nextLine();scanner.nextLine();
                            BosqueDiaAvanzar(scanner);
                        }
                    }
                }
            } else if (ClaseMago) {
                imprimirPocoAPoco("  Dado que eres un mago, sabes de hechizos de barrera.", 20);
                imprimirPocoAPoco("  Intentas crear una barrera mágica.", 20);
                imprimirPocoAPoco("  Posibilidad de fallo del 50%.", 20);
                System.out.println("");
                imprimirPocoAPoco(AMARILLO + "  ¡Atención!, deberás resolver el siguiente minijuego.", 50);
                imprimirPocoAPoco("  Memoriza la secuencia." + RESET, 50);
                System.out.println("  Conjurando barrera...");
                imprimirPocoAPoco("  La secuencia empieza en 5 4 3 2 1", 400);
                System.out.println("");
                PatronMinijuego(); // PatronMinijuego debe estar definida en algún otro lugar del código

                System.out.println("");
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();scanner.nextLine();

                if (Acertijo) { // Acertijo debe ser una variable booleana que indique si el jugador resolvió correctamente el minijuego
                    imprimirPocoAPoco("  Consigues conjurar una barrera protectora para evitar ser atacado desprevenidamente.", 20);
                    imprimirPocoAPoco("  Pasas la noche protegido por tu hechizo de barrera.", 20);
                    System.out.println("");
                    System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                    scanner.nextLine();scanner.nextLine();
                    BosqueDiaAvanzar(scanner);
                } else {
                    imprimirPocoAPoco("  Desafortunadamente el hechizo no se activó.", 20);
                    imprimirPocoAPoco("  Dado que conjurar una barrera mágica consume mucha energía,", 20);
                    imprimirPocoAPoco("  te ves en la incapacidad de intentar conjurar otro.", 20);
                    System.out.println("");
                    imprimirPocoAPoco("  Posibilidad de ser atacado: 50%", 20);
                    imprimirPocoAPoco("  . . .", 400);
                    System.out.println("");

                    int ataquerandom = (int) (Math.random() * 10) + 1;
                    if (ataquerandom <= 5) {
                        imprimirPocoAPoco("  . . .", 400);
                        NivelEnemigo = "Nivel-1";
                        SimularEncuentro();
                        audioPlayer.stop();
                        audioPlayer.setRepeat(true);
                        audioPlayer.play("aves.wav");
                        System.out.println("");
                        imprimirPocoAPoco("  Sobrevives al ataque y pasas la noche.", 20);
                        int gemasrandom = (int) (Math.random() * 20) + 1;
                        Gemas += gemasrandom;
                        imprimirPocoAPoco(AMARILLO + "  Obtuviste +" + gemasrandom + " gemas." + RESET, 20);
                        imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20); // Corregido para mostrar el total de gemas
                        System.out.println("");
                        System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                        scanner.nextLine();scanner.nextLine();
                        BosqueDiaAvanzar(scanner);
                    } else {
                        imprimirPocoAPoco("  . . .", 400);
                        imprimirPocoAPoco("  Pasa la noche y afortunadamente no fuiste atacado.", 20);
                        System.out.println("");
                        System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                        scanner.nextLine();scanner.nextLine();
                        BosqueDiaAvanzar(scanner);
                    }
                }
            } else if (ClaseInvocador) {
                imprimirPocoAPoco("  Como eres un invocador,", 20);
                imprimirPocoAPoco("  decides invocar a un aliado que te proteja durante la noche.", 20);
                imprimirPocoAPoco(MAGENTA + "  ¡Esta invocación se mantendrá hasta tu siguiente combate!" + RESET, 20);
                System.out.println("");
                mostrarMonstruos(monstruosInvocados); // mostrarMonstruos debe estar definida en algún otro lugar del código
                elegirMonstruo(monstruosInvocados); // elegirMonstruo debe estar definida en algún otro lugar del código
                System.out.println("");
                imprimirPocoAPoco("  Posibilidad de ser atacado: 40%", 20);
                imprimirPocoAPoco("  . . .", 400);
                System.out.println("");

                int ataquerandom = (int) (Math.random() * 10) + 1;
                if (ataquerandom <= 4) {
                    imprimirPocoAPoco("  . . .", 400);
                    NivelEnemigo = "Nivel-1";
                    SimularEncuentro();
                    audioPlayer.stop();
                    audioPlayer.setRepeat(true);
                    audioPlayer.play("aves.wav");
                    System.out.println("");
                    imprimirPocoAPoco("  Sobrevives al ataque y pasas la noche.", 20);
                    int gemasrandom = (int) (Math.random() * 20) + 1;
                    Gemas += gemasrandom;
                    imprimirPocoAPoco(AMARILLO + "  Obtuviste +" + gemasrandom + " gemas." + RESET, 20);
                    imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20); // Corregido para mostrar el total de gemas
                    System.out.println("");
                    System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                    scanner.nextLine();scanner.nextLine();
                    BosqueDiaAvanzar(scanner);
                } else {
                    imprimirPocoAPoco("  . . .", 400);
                    imprimirPocoAPoco("  Pasa la noche y afortunadamente no fuiste atacado.", 20);
                    System.out.println("");
                    System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                    scanner.nextLine();scanner.nextLine();
                    BosqueDiaAvanzar(scanner);
                }
            }

        }
        else if (opcion == 2) {
            imprimirPocoAPoco("  Decides seguir caminando, pues para ti, ¡no hay tiempo que perder!", 20);
            imprimirPocoAPoco("  . . .", 400);
            imprimirPocoAPoco("  Mientras caminas con una velocidad sorprendente.", 20);
            imprimirPocoAPoco("  Tus pasos llaman la atención de todo aquel depredador que pueda encontrarse en tu camino.", 20);
            imprimirPocoAPoco("  . . .", 400);

            NivelEnemigo = "Nivel-1";
            SimularEncuentro();
            audioPlayer.stop();
            audioPlayer.setRepeat(true);
            audioPlayer.play("aves.wav");

            System.out.println("");
            imprimirPocoAPoco("  Sobrevives al ataque y pasas la noche.", 20);
            int gemasrandom = (int) (Math.random() * 20) + 1;
            Gemas += gemasrandom;
            imprimirPocoAPoco(AMARILLO + "  Obtuviste +" + gemasrandom + " gemas." + RESET, 20);
            imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20);
            System.out.println("");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();scanner.nextLine();

            imprimirPocoAPoco("  Un enemigo no va a cambiar tu decisión de seguir avanzando.", 20);
            System.out.println("  Avanzando...");
            imprimirPocoAPoco("  . . . . . . . . . . . . . . . .", 400);
            System.out.println("");
            imprimirPocoAPoco("  Se hace de día y te sientes agotado, sin embargo, tu determinación no cesa y continúas.", 20);
            System.out.println("");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();scanner.nextLine();

            DesvioBosque(scanner);

        }
        else {
            imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 15);
            VolverBosqueAldea(scanner);
        }


    }

    //--1-preguntar/2-buscar pista--1-VolverBosque---1-PasarNoche----------------------------------
    private static void BosqueDiaAvanzar(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Una vez de día, tú, " + AMARILLO + NombreJugador + RESET + ", avanzas en tu viaje.", 20);
        imprimirPocoAPoco("  Parece que será un camino un poco largo, pero no tienes prisa.", 20);
        imprimirPocoAPoco("  Después de todo, sabes que difícilmente alguien esté buscando el mismo tesoro que tú.", 20);
        System.out.println("");
        System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
        scanner.nextLine();
        System.out.println("  Caminando");
        imprimirPocoAPoco("  . . . . . . . .", 300);
        System.out.println("");
        if (ComerValla) {
            imprimirPocoAPoco("  En el camino encuentras arbustos de bayas, los mismos que comiste anteriormente.", 20);
            imprimirPocoAPoco("  Comes unas cuantas bayas y el resto te las llevas para más adelante.", 20);
            Cantidades[6] += 3;
            imprimirPocoAPoco(AMARILLO + "  * Bayas: " + Cantidades[6] + RESET, 20);
            System.out.println("");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
        } else {
            imprimirPocoAPoco("  En el camino encuentras arbustos de bayas, son los mismos de la última vez.", 20);
            imprimirPocoAPoco("  Pero desconfías y lo pasas de largo.", 20);
            System.out.println("");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
        }
        System.out.println("");


        DesvioBosque(scanner);
    }

    //--1-preguntar/2-buscar pista--1-VolverBosque---1-PasarNoche/2-Segir--------
    private static void DesvioBosque(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Ves un hermoso río por el camino y te sientes tentado a admirarlo por un momento.", 20);
        imprimirPocoAPoco("  ¿Qué vas a hacer " + AMARILLO + NombreJugador + RESET + "?", 20);
        imprimirPocoAPoco(VERDE + "  1. Observar el río.", 20);
        imprimirPocoAPoco(VERDE + "  2. Continuar el viaje.", 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");

        if (opcion == 1) {
            imprimirPocoAPoco("  Te quedas un momento para observar el río.", 20);
            imprimirPocoAPoco("  No se ve tan profundo, gracias a que el agua es muy cristalina,", 20);
            imprimirPocoAPoco("  logras ver algo dentro enterrado por la arena del río.", 20);
            imprimirPocoAPoco(VERDE + "  1. Tomar el objeto del río.", 20);
            imprimirPocoAPoco(VERDE + "  2. Ignorarlo y continuar el viaje.", 20);
            imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
            int opcion2 = scanner.nextInt();
            System.out.println("");
            if (opcion2 == 1) {
                imprimirPocoAPoco("  Decides tomar el objeto, por lo que te adentras en el río.", 20);
                System.out.println("");
                imprimirPocoAPoco("  . . .", 300);
                System.out.println("");
                imprimirPocoAPoco("  Tomas el objeto, el objeto resultó ser una varita.", 20);
                imprimirPocoAPoco("  Algún aventurero debió perderla en el río.", 20);
                EncontrarVaritaAleatoria();
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();
                scanner.nextLine();
                System.out.println("");
                imprimirPocoAPoco("  Al voltear ves a un elfo del otro lado del río.", 20);
                imprimirPocoAPoco("  Te quedas quieto observando y el elfo hace lo mismo.", 20);
                imprimirPocoAPoco("  " + AMARILLO + NombreJugador + RESET + ", ¿qué vas a hacer?", 20);
                imprimirPocoAPoco("  Has escuchado historias sobre los elfos antes, sabes que son criaturas peligrosas.", 20);
                imprimirPocoAPoco("  Pero parece que este elfo es joven, no debe tener mucha experiencia,", 20);
                imprimirPocoAPoco("  por lo que no corres demasiado peligro, a comparación con un elfo mayor.", 20);
                System.out.println(AZUL + "  Presiona Enter para continuar" + RESET);
                scanner.nextLine();
                System.out.println("");
                imprimirPocoAPoco("  El elfo corre hacia ti, tal parece que la única forma de salvarte es enfrentándolo.", 20);

                NivelEnemigo = "EspecificoElfoJoven";
                SimularEncuentro();
                int GemasRandom = (int) (Math.random() * 20) + 15;
                Gemas += GemasRandom;
                System.out.println("");
                imprimirPocoAPoco("  Tras derrotar al enemigo, consigues unas cuantas gemas.", 20);
                imprimirPocoAPoco(MAGENTA + "  +" + GemasRandom + " gemas." + RESET, 20);
                imprimirPocoAPoco(AMARILLO + "  Gemas: " + Gemas + RESET, 20);
                System.out.println("");
                imprimirPocoAPoco("  Esta batalla es muy complicada, si sigues así perderás contra el elfo joven.", 20);
                imprimirPocoAPoco("  Por lo que ves una oportunidad y escapas lo más rápido posible.", 20);
                System.out.println("");
                imprimirPocoAPoco("  . . .", 300);
                System.out.println("");
                imprimirPocoAPoco("  Parece que el elfo no tiene intenciones de seguirte, por lo que estás a salvo por el momento.", 20);
                imprimirPocoAPoco("  " + AMARILLO + NombreJugador + RESET + ", será mejor que sigas tu camino y llegues a tu destino, lo más pronto posible.", 20);
                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();
                System.out.println("  Cargando.");
                imprimirPocoAPoco("  . . .", 300);
            }
        } else {
            imprimirPocoAPoco("  Eres sabio y prefieres evitar cualquier distracción.", 20);
            imprimirPocoAPoco("  Avanzas en el viaje, mientras observas tu alrededor.", 20);
            imprimirPocoAPoco("  Nunca se sabe qué criatura podría estar al acecho.", 20);
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            System.out.println("  Cargando.");
            imprimirPocoAPoco("  . . .", 300);
        }

        AldeaVernum(scanner);

    }




    //--1-preguntar/2-buscar pista--1-Caminosolitario--------------------------------------
    private static void CaminoTrasAldea(Scanner scanner) {
        audioPlayer.stop();
        audioPlayer.play("caminovaquero.wav");
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Sales de la aldea y te diriges a \"Vernum\" a través de un camino solitario.", 20);
        imprimirPocoAPoco("  Este camino rodea completamente al bosque encantado, una forma ideal si buscas evitar muchos enemigos.", 20);
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");
        imprimirPocoAPoco("  Al cabo de un rato, te topas con un anciano.", 20);
        imprimirPocoAPoco("", 20);
        imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
        imprimirPocoAPoco("  ¡Oh!, joven aventurero, ¿estarías dispuesto a ayudar a este anciano?", 20);
        imprimirPocoAPoco("  Te recompensaré si me ayudas.", 20);
        System.out.println("");
        imprimirPocoAPoco(VERDE + "  1. Ayudar al anciano, que huele extraño.", 20);
        imprimirPocoAPoco(VERDE + "  2. Huir del anciano, porque huele raro." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        System.out.println("");

        if (opcion == 1) {
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  Dígame, ¿cómo le puedo ayudar?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Estoy en busca de una planta medicinal, una planta común, pero muy especial para mí.", 20);
            imprimirPocoAPoco("  Verás, trabajo en una tienda de pociones,", 20);
            imprimirPocoAPoco("  pero esta planta tiene la capacidad de curar a mi esposa de una enfermedad.", 20);
            imprimirPocoAPoco("  Esta planta es conocida como \"Herba Aurum\".", 20);

            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  ¿Dónde puedo encontrarla?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Afortunadamente, se encuentra muy fácilmente por esta zona del bosque encantado.", 20);
            imprimirPocoAPoco("  Tendrás que entrar al bosque y buscar, no será complicado.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  Si no es complicado conseguirla, ¿por qué me pide ayudarlo?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  La razón es simple, estoy viejo, cualquier criatura, por menos peligrosa que sea,", 20);
            imprimirPocoAPoco("  puede vencerme fácilmente, en cambio tú eres joven y con mucha fuerza.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  Entiendo, yo le ayudaré a encontrar \"Herba Aurum\", para que pueda curar a su esposa.", 20);
            System.out.println("");
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            scanner.nextLine();
            imprimirPocoAPoco("  . . .", 300);
            System.out.println("");

            imprimirPocoAPoco("  Con una sensación de ironía, a pesar de querer evitar entrar al bosque encantado, deberás adentrarte de nuevo para ayudar al anciano.", 20);
            imprimirPocoAPoco("  Parece que el destino tiene otros planes para ti y decides aceptarlo.", 20);

        PlantaMedicinal(scanner);


        }else {
          IgnorarAnciano(scanner);
        }




    }
    private static void PlantaMedicinal(Scanner scanner) {
        audioPlayer.stop();
        audioPlayer.play("aves.wav");
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");
        imprimirPocoAPoco("  Después de una larga caminata, finalmente llegas a un claro.", 20);
        imprimirPocoAPoco("  Ante tus ojos se extiende un vasto campo lleno de plantas con hojas de punta dorada.", 20);
        imprimirPocoAPoco("  El lugar parecía sacado de un sueño,", 20);
        imprimirPocoAPoco("  pero lo que más llamó tu atención fueron las ruinas esparcidas por el campo.", 20);
        imprimirPocoAPoco("  Parecían ser restos de una antigua ciudad, ahora reclamados por la naturaleza.", 20);
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");
        imprimirPocoAPoco("  Recoges un puñado de estas plantas, asegurándote así de llevar las suficientes.", 20);
        imprimirPocoAPoco("  Te regresas por donde viniste, para encontrarte con el anciano.", 20);
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");
        audioPlayer.stop();
        audioPlayer.play("caminovaquero.wav");
        imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
        imprimirPocoAPoco("  Hola señor, ya regresé y le conseguí su planta medicinal.", 20);
        System.out.println("");
        imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
        imprimirPocoAPoco("  Muchas gracias, joven aventurero.", 20);
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");
        imprimirPocoAPoco("  " + ROJO + "Bandido:" + RESET, 40);
        imprimirPocoAPoco("  Hola, ¿cómo están? ¿todo bien? ... Excelente.", 20);
        imprimirPocoAPoco("  Necesito que por favor cooperen y me entreguen todas sus pertenencias de valor.", 20);
        imprimirPocoAPoco("  Si me enfrentan, entonces el anciano morirá.", 20);
        System.out.println("");

        imprimirPocoAPoco(VERDE + "  1. Enfrentarse al bandido.", 20);
        imprimirPocoAPoco(VERDE + "  2. Entregarle los objetos de valor." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        System.out.println("");

        if (opcion == 1) {
            imprimirPocoAPoco("  Decides enfrentar al bandido.", 20);
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            scanner.nextLine();

            NivelEnemigo = "EspecificoBandido";
            SimularEncuentro();
            audioPlayer.stop();
            audioPlayer.play("caminovaquero.wav");

            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Eso fue muy peligroso.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  No podía permitir que alguien como él nos robara.", 20);
            System.out.println();
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Comprendo...", 20);
        } else {
            imprimirPocoAPoco("  Decides evitar cualquier disputa con el bandido y obedeces a su petición.", 20);
            if (VaritaActiva[1]) {
                VaritaActiva[1] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de fuego." + RESET, 20);
            } else if (VaritaActiva[2]) {
                VaritaActiva[2] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de hielo." + RESET, 20);
            } else if (VaritaActiva[3]) {
                VaritaActiva[3] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de debilidad." + RESET, 20);
            } else if (VaritaActiva[4]) {
                VaritaActiva[4] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de curación." + RESET, 20);
            } else if (VaritaActiva[5]) {
                VaritaActiva[5] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de protección." + RESET, 20);
            }

            if (Gemas >= 50) {
                Gemas -= 25;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó 25 gemas." + RESET, 20);
            } else if (Gemas < 50 && Gemas > 20) {
                Gemas -= 20;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó 20 gemas." + RESET, 20);
            } else if (Gemas <= 20) {
                Gemas = 0;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó todas las gemas." + RESET, 20);
            }

            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            scanner.nextLine();

            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Es una lástima que te robaran tus cosas.", 20);
        }

        imprimirPocoAPoco("  Por favor, vayamos a mi casa.", 20);
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");

        imprimirPocoAPoco("  Ambos regresan a la aldea, para ir a la casa del anciano.", 20);

        CasaAnciano(scanner);

    }
    private static void CasaAnciano(Scanner scanner) {
        audioPlayer.stop();
        audioPlayer.play("medieval.wav");
        ContadorRecargaVaritasViajando();
        imprimirPocoAPoco("  Una vez en la casa del anciano, él grita.", 20);
        imprimirPocoAPoco("  \"¡Querida, ya he regresado!\"", 20);
        imprimirPocoAPoco("  Y se apresura a sacar otras hierbas y frascos para preparar la medicina.", 20);
        imprimirPocoAPoco("  . . .", 300);
        imprimirPocoAPoco("  Mientras prepara la medicina.", 20);
        imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
        imprimirPocoAPoco("  Sé que no fue una tarea muy complicada, pero igual estoy agradecido.", 20);
        imprimirPocoAPoco("  De solo pensar que si no fuera por ti, quién sabe qué me hubiera pasado al encontrarme con el bandido.", 20);
        System.out.println("");
        imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
        imprimirPocoAPoco("  No piense en ello ahora, lo que pasó ya pasó. Hay que agradecer estar aquí en este momento.", 20);
        System.out.println("");
        imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
        imprimirPocoAPoco("  Sabias palabras para un aventurero tan joven.", 20);
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");
        imprimirPocoAPoco("  Después de un rato, el anciano termina la medicina y se la da a su esposa.", 20);
        imprimirPocoAPoco("  La deja descansando en su cama mientras la medicina hace efecto.", 20);
        System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
        scanner.nextLine();

        imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
        imprimirPocoAPoco("  Dime, ¿qué clase de aventurero eres?", 20);
        imprimirPocoAPoco("  Déjame adivinar.", 20);
        System.out.println("");
        imprimirPocoAPoco("  El anciano busca entre sus cosas en un mueble hecho a mano.", 20);
        imprimirPocoAPoco("  Una caja encima del mueble, saca algo de ella mientras dice...", 20);
        System.out.println("");

        if (ClaseGuerrero) {
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Dado que no llevas un bastón mágico, un objeto que distingue a los magos,", 20);
            imprimirPocoAPoco("  y tampoco llevas contigo objetos de invocación,", 20);
            imprimirPocoAPoco("  puedo asumir que eres de la clase guerrero, un guerrero valiente y bondadoso.", 20);
            System.out.println("");
            imprimirPocoAPoco("  Dime, ¿tienes la habilidad de reducir el daño que recibes?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  Así es, señor.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Excelente. Como te había dicho antes, te daré tu recompensa por haberme ayudado.", 20);
            imprimirPocoAPoco("  Voy a dibujar un círculo mágico en el suelo y te pondrás dentro de él.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  No comprendo, ¿por qué debo hacerlo?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Potenciaré tu habilidad.", 20);
            imprimirPocoAPoco("  Haré que seas capaz de soportar más el daño que recibas.", 20);
            System.out.println("");
            imprimirPocoAPoco("  . . .", 300);
            System.out.println("");
            imprimirPocoAPoco("  Sientes dudas al respecto, pero sabes que no es mala persona y decides obedecer.", 20);
            System.out.println("  Conjurando...");
            imprimirPocoAPoco("  . . . . . . . . . . .", 300);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Dime, ¿cómo te sientes ahora?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  No me siento diferente.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Excelente, significa que no hubo error al potenciar tu habilidad.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  Ah, ¿gracias?", 20);
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();

            imprimirPocoAPoco("  El anciano toma otras cosas y te las da.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Toma estas cosas, sé que te podrán ayudar en tu viaje.", 20);
            imprimirPocoAPoco("  Por el tiempo que te hice perder, te daré un caballo para que llegues más rápido a tu destino.", 20);
            System.out.println("");
            imprimirPocoAPoco("  Agradeces al anciano y te retiras del lugar, subes al caballo y te diriges a Verum.", 20);

            System.out.println("");
            imprimirPocoAPoco("  . . .", 300);
            System.out.println("");
            imprimirPocoAPoco(AMARILLO + "  Objetos obtenidos:" + RESET, 20);
            Gemas += 15;
            imprimirPocoAPoco(MAGENTA + "  * Gemas +15", 20);
            Cantidades[0] += 1;
            imprimirPocoAPoco("  * Poción de curación +1", 20);

            ReduccionGuerreroDañoObtenido = 0.7;
            HabilidadEscudoMagicoGuerrero = 8;
            imprimirPocoAPoco("  * Reducción de daño por clase guerrero +10%.", 20);
            imprimirPocoAPoco("  * Habilidad de escudo mágico por clase guerrero +3." + RESET, 20);

        }
        else if (ClaseMago) {
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Dado que llevas un bastón mágico, un objeto que distingue muy bien a los magos,", 20);
            imprimirPocoAPoco("  puedo asumir que eres un aventurero de la clase mago, ¿me equivoco?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  Tiene toda la razón, señor.", 20);
            imprimirPocoAPoco("  ¿Pero por qué me dice esto?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Me prestarás tu bastón un momento.", 20);
            System.out.println("");
            imprimirPocoAPoco("  Aunque estás desconcertado al respecto, le entregas tu bastón.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Te doy a elegir,", 20);
            imprimirPocoAPoco(VERDE + "  1. Aumentar las capacidades mágicas de los conjuros.", 20);
            imprimirPocoAPoco(VERDE + "  2. Aumentar la capacidad máxima del mana del bastón.", 20);
            imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
            int opcion = scanner.nextInt();
            imprimirPocoAPoco("  . . .", 300);
            System.out.println("");

            imprimirPocoAPoco("  Tu decisión es clara.", 20);
            if (opcion == 2){
                imprimirPocoAPoco("  El anciano saca una botella de elixir de mana, un objeto mágico muy raro.", 20);
                imprimirPocoAPoco("  Y lo vierte sobre tu bastón, el bastón absorbe el elixir y brilla por un momento.", 20);
                System.out.println("");
                imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
                imprimirPocoAPoco("  Con esta cantidad de elixir de mana, tu bastón deberá tener +5 cargas de mana.", 20);
            } else{
                imprimirPocoAPoco("  El anciano saca un cristal de poder. Existen diversos tipos de cristales de poder con diferentes capacidades de aumentación.", 20);
                imprimirPocoAPoco("  Aunque es un objeto raro y poderoso, no deja de ser un objeto.", 40);
                imprimirPocoAPoco("  El anciano junta la punta de tu bastón con el cristal de poder y el cristal deja de brillar.", 20);
                imprimirPocoAPoco("  Esta es una clara señal de que ha funcionado y las capacidades mágicas del bastón han aumentado.", 40);
                System.out.println("");
                imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
                imprimirPocoAPoco("  Me imagino que sabrás lo que es esto, aunque debo decirte que no es un objeto muy poderoso,", 20);
                imprimirPocoAPoco("  ya que las capacidades mágicas de este cristal eran muy débiles.", 40);
                imprimirPocoAPoco("  Antes de que te vayas, te entregaré algo más y te daré un caballo para que llegues a tu destino lo más rápido posible.", 40);
            }
            System.out.println("");
            imprimirPocoAPoco("  Agradeces al anciano y te retiras del lugar, subes al caballo y te diriges a Verum.", 20);
            System.out.println("");
            imprimirPocoAPoco("  . . .", 300);
            System.out.println("");
            imprimirPocoAPoco(AMARILLO + "  Objetos obtenidos:" + RESET, 20);
            Gemas += 15;
            imprimirPocoAPoco(MAGENTA + "  * Gemas +15", 20);
            Cantidades[0] += 1;
            imprimirPocoAPoco("  * Poción de curación +1", 20);
            if (opcion == 2){
                EnergiaVarita[0] += 5;
                MaxEnergiaVarita[0] = 15;
                imprimirPocoAPoco("  * Energía máxima del bastón +5" + RESET, 20);
            }else{
                NivelBaston_1 = false;
                NivelBaston_2 = true;
                imprimirPocoAPoco("  * Nivel de bastón: 2" + RESET, 20);
            }

        }
        else if (ClaseInvocador) {
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Dado que no llevas un bastón mágico, un objeto que distingue a los magos,", 20);
            imprimirPocoAPoco("  pero tampoco noto cualidades de un guerrero,", 20);
            imprimirPocoAPoco("  puedo asumir que eres un aventurero de la clase invocador, ¿me equivoco?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  Tiene toda la razón, señor.", 20);
            imprimirPocoAPoco("  ¿Pero por qué me dice esto?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Bueno, como te dije, te voy a recompensar por haberme ayudado.", 20);
            imprimirPocoAPoco("  Así que te daré algunas cosas que sé que te van a servir mucho.", 20);
            System.out.println("");
            imprimirPocoAPoco("  El anciano toma varias cosas y te las entrega, entre ellas un pergamino.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Anciano:" + RESET, 40);
            imprimirPocoAPoco("  Te daré un caballo para que puedas ir a tu destino más rápido.", 20);
            System.out.println("");
            imprimirPocoAPoco("  Agradeces al anciano y te retiras montando a caballo hacia Verum.", 20);
            System.out.println("");
            imprimirPocoAPoco("  . . .", 300);
            System.out.println("");
            imprimirPocoAPoco(AMARILLO + "  Objetos obtenidos:" + RESET, 20);
            Gemas += 15;
            imprimirPocoAPoco(MAGENTA + "  * Gemas +15", 20);
            Cantidades[0] += 1;
            imprimirPocoAPoco("  * Poción de curación +1", 20);
            monstruosInvocados.add(8); // Espectro Sombrío
            imprimirPocoAPoco("  * Nueva invocación obtenida: Espectro sombrío" + RESET, 20);
        }
        System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
        scanner.nextLine();
        scanner.nextLine();

        AldeaVernum(scanner);
    }



    private static void IgnorarAnciano(Scanner scanner) {
        imprimirPocoAPoco("  Decidiste ignorar las súplicas del anciano.", 20);
        imprimirPocoAPoco("  y continúas tu camino hacia la aldea Vernum.", 20);
        System.out.println("");
        imprimirPocoAPoco("  La ruta alternativa te lleva a través de un paisaje árido y desolado,", 20);
        imprimirPocoAPoco("  muy diferente al denso y sombrío bosque encantado.", 20);
        System.out.println("");
        imprimirPocoAPoco("  El sol ardía intensamente en el cielo sin nubes.", 20);
        imprimirPocoAPoco("  La tierra, seca y agrietada, parecía absorber cada rayo de luz, haciendo que el calor fuera casi insoportable.", 20);
        imprimirPocoAPoco("  Mientras caminas, notas que la vida en esta región es escasa.", 20);
        imprimirPocoAPoco("  Solo algunas plantas resistentes, como cactus y pequeños arbustos espinosos, logran sobrevivir en este entorno hostil. ", 20);
        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");
        imprimirPocoAPoco("  Después de varias horas de caminata bajo el sol abrasador.", 20);
        imprimirPocoAPoco("  Notas una figura a lo lejos, parece ser una persona y te acercas.", 20);
        imprimirPocoAPoco("  El hombre vestía ropas desgastadas y un turbante para protegerse del sol.", 20);
        System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
        scanner.nextLine();
        System.out.println("");
        imprimirPocoAPoco("  " + ROJO + "Bandido:" + RESET, 40);
        imprimirPocoAPoco("  ¡Alto ahí, viajero!", 20);
        imprimirPocoAPoco("  Este es mi territorio, y tendrás que pagar un peaje para pasar.", 20);
        System.out.println("");
        imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
        imprimirPocoAPoco("  ¿Qué pasa si me niego?", 20);
        System.out.println("");
        imprimirPocoAPoco("  " + ROJO + "Bandido:" + RESET, 40);
        imprimirPocoAPoco("  Si lo haces, pronto lo averiguaremos.", 20);
        System.out.println("");
        imprimirPocoAPoco("  ¿Qué vas a hacer?", 20);
        imprimirPocoAPoco(VERDE + "  1. Enfrentarse al bandido.", 20);
        imprimirPocoAPoco("  2. Entregarle los objetos de valor." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        System.out.println("");

        if (opcion == 1) {
            imprimirPocoAPoco("  Decides enfrentarte al bandido.", 20);
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            EnemigoEspecificoBandido();
            SimularEncuentro();
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  Bueno, ya averiguaré.", 20);
            System.out.println("");
            imprimirPocoAPoco("  Sigues tu camino con orgullo y alegría por haber derrotado al bandido.", 20);
            imprimirPocoAPoco("  Como compensación por las molestias, recoges algunas gemas que tenía el bandido.", 20);
            Gemas += 30;
            imprimirPocoAPoco(MAGENTA + "  * Gemas: +30." + RESET, 20);
        } else {
            imprimirPocoAPoco("  Decides evitar cualquier disputa con el bandido y obedecer su petición.", 20);
            if (VaritaActiva[1]) {
                VaritaActiva[1] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de fuego." + RESET, 20);
            } else if (VaritaActiva[2]) {
                VaritaActiva[2] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de hielo." + RESET, 20);
            } else if (VaritaActiva[3]) {
                VaritaActiva[3] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de debilidad." + RESET, 20);
            } else if (VaritaActiva[4]) {
                VaritaActiva[4] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de curación." + RESET, 20);
            } else if (VaritaActiva[5]) {
                VaritaActiva[5] = false;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó la varita de protección." + RESET, 20);
            }

            if (Gemas >= 50) {
                Gemas -= 25;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó 25 gemas." + RESET, 20);
            } else if (Gemas > 20) {
                Gemas -= 20;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó 20 gemas." + RESET, 20);
            } else if (Gemas <= 20) {
                Gemas = 0;
                imprimirPocoAPoco(ROJO + "  El enemigo te robó todas las gemas." + RESET, 20);
            }
            System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();
            imprimirPocoAPoco("  Aún desconcertado por haber tenido que entregar tus pertenencias, continúas tu camino.", 20);
            imprimirPocoAPoco("  Te animas pensando que al encontrar el tesoro, este altercado será solo un evento insignificante en tu vida.", 20);
        }

        AldeaVernum(scanner);
    }

    private static void AldeaVernum(Scanner scanner) {
        borrarConsola(1000);
        audioPlayer.stop();
        audioPlayer.play("medieval.wav");
        ContadorRecargaVaritasViajando();
        System.out.println("");
        imprimirPocoAPoco("  Tras ese largo viaje, ves a lo lejos la aldea Vernum.", 20);
        imprimirPocoAPoco("  " + AMARILLO + NombreJugador + RESET + ", sientes una gran emoción, sabes que has llegado lejos.", 20);
        imprimirPocoAPoco("  No te detendrás ahora, cumplirás tu objetivo, cueste lo que cueste.", 20);

        System.out.println("");
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");

        imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
        imprimirPocoAPoco("  ¡Hola, joven aventurero! Nos volvemos a encontrar.", 20);
        imprimirPocoAPoco("  Me imaginaba encontrarte en otro lugar, así que es una grata sorpresa verte aquí.", 20);
        System.out.println("");
        imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);

        imprimirPocoAPoco(VERDE + "  1. Estoy en busca de un tesoro.", 20);
        imprimirPocoAPoco("  2. También me es grato encontrarte, señor." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        borrarConsola(4);

        if (opcion == 1) {
            System.out.println("  Estoy en busca de un tesoro.");
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
            imprimirPocoAPoco("  No es sorprendente, muchos aventureros están siempre en busca de tesoros en algún lugar.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco("  Supongo que eso es bueno para su negocio.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
            imprimirPocoAPoco("  En efecto, siempre busco tener los mejores productos y precios accesibles.", 20);
            imprimirPocoAPoco("  ¿Te interesaría comprar algo de mi mercancía?", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco(VERDE + "  (1. Sí) (2. No)" + RESET, 15);
            imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
            int opcion2 = scanner.nextInt();
            System.out.println("");
            if (opcion2 == 1) {
                comerciante();
                System.out.println("");
                imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
                imprimirPocoAPoco("  ¡Gracias por comprar! Espero nos volvamos a ver muy pronto.", 20);
            } else {
                imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
                imprimirPocoAPoco("  Está bien, otro día será. ¡Cuidate mucho hasta entonces!", 20);
            }

        } else {
            imprimirPocoAPoco("  También me es grato encontrarte, señor.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
            imprimirPocoAPoco("  Veo que el sentimiento es mutuo.", 20);
            imprimirPocoAPoco("  Debo advertirte que si tu intención es pescar o explorar en estas aguas cerca de la aldea,", 20);
            imprimirPocoAPoco("  es peligroso hacerlo, ya que habitan criaturas peligrosas, especialmente en esta época del año.", 20);
            imprimirPocoAPoco("  Me dirijo a esta aldea para suministrar medicinas a los aldeanos.", 20);
            imprimirPocoAPoco("  Sé que puede parecer que te lo digo para que me compres algo, pero te aseguro que no es así.", 20);
            imprimirPocoAPoco("  Sin embargo, si estás interesado en comprar algo, no me molestaría en lo absoluto.", 20);
            System.out.println("");
            imprimirPocoAPoco("  " + AMARILLO + NombreJugador + ":" + RESET, 40);
            imprimirPocoAPoco(VERDE + "  (1. Comprar) (2. No comprar)" + RESET, 15);
            imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
            int opcion2 = scanner.nextInt();
            System.out.println("");
            if (opcion2 == 1) {
                comerciante();
                System.out.println("");
                imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
                imprimirPocoAPoco("  ¡Gracias por comprar! Espero nos volvamos a ver muy pronto.", 20);
            } else {
                imprimirPocoAPoco("  " + CYAN + "Comerciante:" + RESET, 40);
                imprimirPocoAPoco("  Está bien, otro día será. ¡Cuídate mucho hasta entonces!", 20);
            }

        }

        System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
        scanner.nextLine();
        imprimirPocoAPoco("  . . .", 300);
        System.out.println("");

        BuscarBote(scanner);
    }

    private static void BuscarBote(Scanner scanner) {
        borrarConsola(1000);
        System.out.println("");
        imprimirPocoAPoco(AMARILLO + "  Lamentablemente, el juego aún está en desarrollo.", 20);
        imprimirPocoAPoco("  Debo felicitarte, has completado el 50% de esta línea de la historia.", 20);
        imprimirPocoAPoco(VERDE + "  Final 1 y 2 de 6", 20);
        imprimirPocoAPoco("", 20);

        imprimirPocoAPoco("  Como compensación, si ayudaste al anciano, podrás probar la recompensa de este.", 20);
        imprimirPocoAPoco("  Pero si no lo ayudaste, aún así puedes elegir tener un enfrentamiento más o terminar hasta aquí.", 20);
        imprimirPocoAPoco(VERDE + "  1. Combatir", 20);
        imprimirPocoAPoco("  2. Finalizar" + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Ya sabes qué hacer, no debo recordártelo." + RESET, 20);

        int opcion = scanner.nextInt();
        scanner.nextLine();  // Consumir el salto de línea

        if (opcion == 1) {
            NivelEnemigo = "Nivel-2";
            SimularEncuentro();
            System.out.println(CYAN + "  Presiona Enter para finalizar y cerrar el juego." + RESET);
            scanner.nextLine();
            imprimirPocoAPoco("  ¡Gracias por jugar!", 20);
            imprimirPocoAPoco("  . . . . . . .", 500);
            System.exit(0);
        } else {
            System.out.println("");
            imprimirPocoAPoco("  ¡Gracias por jugar!", 20);
            imprimirPocoAPoco("  . . . . . . .", 500);
            System.exit(0);
        }

        // El código que sigue a continuación nunca se ejecutará debido a la salida del sistema en las líneas anteriores

        imprimirPocoAPoco("  Después de ese inesperado encuentro, retomas tu camino.", 20);
        imprimirPocoAPoco("  Ahora deberás buscar alguna forma de llegar hasta la isla.", 20);
        imprimirPocoAPoco("  Ya dentro de la aldea Vernum...", 20);
        imprimirPocoAPoco("  Observas a los aldeanos que van y vienen, comerciando productos y compartiendo noticias.", 20);
        imprimirPocoAPoco("  Te lo imaginabas diferente; a pesar de su aspecto modesto, la aldea tenía un aire acogedor y tranquilo.", 20);
    }











    //--------2-SenderoIzquierda--------------------------------------------------------
    private static void senderoIzquierda(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        imprimirPocoAPoco("  Caminas por el sendero de la izquierda.", 20);
        imprimirPocoAPoco("  Llegas a un lugar con muchos árboles, aun siendo un bosque hay demasiados árboles.", 20);
        imprimirPocoAPoco(VERDE + "  1. Subir a un árbol para observar.", 20);
        imprimirPocoAPoco("  2. Continuar caminando en medio de los árboles." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();
        System.out.println("");
        if (opcion == 1) {
            subirArbol(scanner);
        } else if (opcion == 2) {
            seguirCaminandoIzquierda(scanner);
        } else {
            imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 15);
            senderoIzquierda(scanner);
        }
    }

    //--------2-SenderoIzquierda----1-SubirArbol---------------------------------------------------
    private static void subirArbol(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        imprimirPocoAPoco("  Subes a un árbol para observar.", 20);
        imprimirPocoAPoco("  Crees ver algo al otro lado del bosque, pero otros árboles te estorban la vista.", 20);
        imprimirPocoAPoco(VERDE + "  1. Bajar y seguir caminando.", 20);
        imprimirPocoAPoco(VERDE + "  2. Saltar a otro árbol.", 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 20);
        int opcion = scanner.nextInt();
        System.out.println("");
        if (opcion == 1) {
            imprimirPocoAPoco("  Bajas y sigues caminando hasta encontrar el tesoro. ¡Felicidades!", 20);



        } else if (opcion == 2) {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  Intentas saltar a otro árbol y te caes. Fin del juego." + RESET, 20);
        } else {
            imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 15);
            subirArbol(scanner);
        }
    }

    //--------2-SenderoIzquierda------2-SeguirCaminando-------------------------------------------------
    private static void seguirCaminandoIzquierda(Scanner scanner) {
        ContadorRecargaVaritasViajando();
        imprimirPocoAPoco("  Sigues caminando.", 20);
        imprimirPocoAPoco("  Al estar tan solo, te cuestionas a ti mismo y a la vida.", 20);
        System.out.println("");
        imprimirPocoAPoco("  Encuentras un río que al parecer recorre todo el bosque encantado.", 20);
        imprimirPocoAPoco("  Tienes mucha sed...", 20);
        imprimirPocoAPoco(VERDE + "  1. Beber agua del río.", 20);
        imprimirPocoAPoco("  2. Seguir sin beber." + RESET, 20);
        imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 15);
        int opcion = scanner.nextInt();

        if (opcion == 1) {
            System.out.println("Bebes agua del arroyo y te sientes revitalizado.");
            System.out.println("Tal parece que el bosque encantado le proporciona ciertas propiedades curativas al río.");
            System.out.println("Por suerte tienes una botella de cristal y la usas para llenarla de agua del río.");
            Cantidades[0] = Cantidades[0] + 1;
            System.out.println(MAGENTA + "Has obtenido poción de curación + 1" + RESET);

            int RandomObtenerVarita = (int) (Math.random() * 10) + 1;
            if (RandomObtenerVarita <= 1) {
                int RandomVarita = (int) (Math.random() * 5) + 1;
                VaritaActiva[RandomVarita] = true;
                System.out.println("");
                System.out.println("Tal parece habia una varita dentro del rio.");
                System.out.println("Tomas la varita y te la quedas.");
                System.out.println(MAGENTA + "Has obtenido " + NombreVarita[RandomVarita]);
            }


        } else if (opcion == 2) {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  Sigues caminando sin beber y te desmayas por la deshidratación. Fin del juego." + RESET, 20);
        } else {
            imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 15);
            seguirCaminandoIzquierda(scanner);
        }
    }//Falta continuar


    //NivelEnemigo = "Nivel-1";
    //NivelEnemigo = "Nivel-2";
    //NivelEnemigo = "Nivel-3";
    //NivelEnemigo = "EspecificoBandido";
    //NivelEnemigo = "EspecificoElfoJoven";
    //NivelEnemigo = "EspecificoDuende";


/*         String nivel = scanner.nextLine().trim();
                    switch (nivel) {
                        case "1":
                              break;
                        case "2":
                              break;
                        default:
                            imprimirPocoAPoco(ROJO + "  Opción inválida. Intenta de nuevo." + RESET, 10);
                            scanner.nextLine();
                            InfoVaritas(scanner);
                            return;
                    }*/

    //audioPlayer.stop();
    //audioPlayer.setRepeat(true);
    //   audioPlayer.play("inicio.wav");
    /* System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
            scanner.nextLine();scanner.nextLine();*/

//int ClaseRandom = (int) (Math.random() * 2) + 1; //En efecto 2 numero maximo
// ContadorRecargaVaritasViajando();  (Llevar siempre en todas.)

    //Funciones de juego-----------------------------------------------------------------------------------------------------------------------------------------

    private static void EncantamientoArmaduraEspinas_Activo(){
        if (SaludArmaduraActual > Salud[1] && EncantamientoArmaduraEspinas){
            DañoArmadura = SaludArmaduraActual - Salud[1];
            int DañoEspinas = (int)(DañoArmadura * 0.3);
            if (DañoEspinas < 1){DañoEspinas = 1;}
            SaludEnemigo -= DañoEspinas;
            imprimirPocoAPoco(VERDE + "  ->> Daño a enemigo por armadura de espinas: " + DañoEspinas + RESET, 10);
            imprimirPocoAPoco(VERDE + "  ->> Salud de enemigo actual: " + SaludEnemigo + RESET, 10);
        }
        if (Salud[1] <= 0){
            EncantamientoArmaduraEspinas = false;
        }

    }
    private static void EncantamientoArmaduraReparación_Activo(){
        if (SaludArmaduraActual > Salud[1] && Salud[1] > 0 && EncantamientoArmaduraReparación) {
            Random random = new Random();
            int opcion = random.nextInt(2);
            switch (opcion) {
                case 0:
                    //Nada
                case 1:
                    int ReparaciónArmadura = SaludArmaduraActual - Salud[1];
                    int ReparaciónArmadura2 = random.nextInt(ReparaciónArmadura) + 1;
                    Salud[1] += ReparaciónArmadura2;
                        imprimirPocoAPoco(VERDE + "  ->> Tu armadura repara parte del daño recibido: " + ReparaciónArmadura2 + RESET, 10);
                    imprimirPocoAPoco(VERDE + "  ->> Salud de la armadura actual: " + Salud[1] + RESET, 10);
                default:

            }
        }
        else if (Salud[1] <= 0){
            EncantamientoArmaduraReparación = false;
        }
    }
    private static void EncantamientoArmaduraAgilidad_Activo(){
        if  (Salud[1] > 0 && EncantamientoArmaduraAgilidad){
        Agilidad[5] = 2;
        }
        else if (Salud[1] <= 0){
            Agilidad[5] = 0;
            EncantamientoArmaduraAgilidad = false;
        }
    }
    private static void EncantamientoArmaduraRecarga_Activo(){

        if  (SaludArmaduraActual > Salud[1] && Salud[1] > 0 && EncantamientoArmaduraRecarga){
            PuntosRecargaArmadura++;
            imprimirPocoAPoco(VERDE + "  ->> Puntos de recarga: " + PuntosRecargaArmadura + "/2"+ RESET, 10);
            if (PuntosRecargaArmadura >=2) {
                PuntosRecargaArmadura = 0;

                imprimirPocoAPoco(VERDE + "  ->> La energia almacenada por la armadura se tranfiere a todas las varitas: +1" + RESET, 10);
                EnergiaVarita[0]++; if (EnergiaVarita[0] > MaxEnergiaVarita[0]){EnergiaVarita[0] = MaxEnergiaVarita[0]; }
                EnergiaVarita[1]++; if (EnergiaVarita[1] > MaxEnergiaVarita[1]){EnergiaVarita[1] = MaxEnergiaVarita[1]; }
                EnergiaVarita[2]++; if (EnergiaVarita[2] > MaxEnergiaVarita[2]){EnergiaVarita[2] = MaxEnergiaVarita[2]; }
                EnergiaVarita[3]++; if (EnergiaVarita[3] > MaxEnergiaVarita[3]){EnergiaVarita[3] = MaxEnergiaVarita[3]; }
                EnergiaVarita[4]++; if (EnergiaVarita[4] > MaxEnergiaVarita[4]){EnergiaVarita[4] = MaxEnergiaVarita[4]; }
                EnergiaVarita[5]++; if (EnergiaVarita[5] > MaxEnergiaVarita[5]){EnergiaVarita[5] = MaxEnergiaVarita[5]; }

            }

        }
        else if (Salud[1] <= 0){
            EncantamientoArmaduraRecarga = false;
        }
    }


    private static void EncantamientoEscudoEspinas_Activo(){
        if (SaludEscudoActual > Salud[2] && EncantamientoEscudoEspinas){
            DañoEscudo = SaludEscudoActual - Salud[2];
            int DañoEspinas = (int)(DañoEscudo * 0.6);
            if (DañoEspinas < 1){DañoEspinas = 1;}
            SaludEnemigo -= DañoEspinas;
            imprimirPocoAPoco(VERDE + "  ->> Daño a enemigo por escudo de espinas: " + DañoEspinas + RESET, 10);
            imprimirPocoAPoco(VERDE + "  ->> Salud de enemigo actual: " + SaludEnemigo + RESET, 10);
        }
        if (Salud[2] <= 0){
            EncantamientoEscudoEspinas = false;
        }
    }
    private static void EncantamientoEscudoBloqueo_Activo(){
        BloqueoEncantamiento = false;
        if (EncantamientoEscudoBloqueo && Salud[2] > 0){
            Random random = new Random();
            BloqueoEncantamiento = Math.random() < 0.37;
        }
        else if (Salud[2] <= 0){
            EncantamientoEscudoBloqueo = false;
        }
    }
    private static void EncantamientoEscudoResistencia_Activo(){
        ResistenciaEscudo = false;
        if (EncantamientoEscudoResistencia && Salud[2] > 0){
            Random random = new Random();
            ResistenciaEscudo = Math.random() < 0.46;
        } else if (Salud[2] <= 0) {
            EncantamientoEscudoResistencia = false;
        }
    }
    private static void EncantamientoEscudoParalisis_Activo(){
        ParalisisPorEscudo = false;
        if (EncantamientoEscudoParalisis && Salud[2] > 0){
            Random random = new Random();
            ParalisisPorEscudo = Math.random() < 0.33;
            if (ParalisisPorEscudo){
                imprimirPocoAPoco(VERDE + "  ->> El enemigo fue paralizado por encantamiento a escudo de paralisis." +  RESET, 10);
                imprimirPocoAPoco(VERDE + "  ->> Se aplicara en el sigiente turno." + RESET, 10);
            }
        } else if (Salud[2] <= 0) {
            EncantamientoEscudoParalisis = false;
        }
    }


    private static void EncantamientoEspadaCritico_Activo(){
        CriticoEspada = false;
        if (EncantamientoEspadaCritico && Salud[3] > 0){
            Random random = new Random();
            CriticoEspada = Math.random() < 0.6;
        } else if (Salud[3] <= 0) {
            EncantamientoEspadaCritico = false;
        }
    }
    private static void EncantamientoEspadaCurativo_Activo(){
        if (EncantamientoEspadaCurativo && Salud[3] > 0){
            Random random = new Random();


            int opcion = random.nextInt(2);
            switch (opcion) {
                case 0:
                    //Nada
                case 1:
                    int ObtenerSaludPorDañoEspada = random.nextInt(Daño[1]) + 1;
                    Salud[0] += ObtenerSaludPorDañoEspada;
                    imprimirPocoAPoco(VERDE + "  ->> La espada otorga salud al jugador: +" + ObtenerSaludPorDañoEspada + RESET, 10);
                    if (Salud[0] > SaludMax[0]) {
                        Salud[0] = SaludMax[0];
                        imprimirPocoAPoco(CYAN + "  !* La salud otorgada por espada supera el límite de la salud máxima del jugador.", 10);
                        imprimirPocoAPoco("  !* La salud aplicada sobrante se ve negada." + RESET, 10);
                        System.out.println("");
                    }
                    imprimirPocoAPoco(MAGENTA + "  <*> Salud de jugador actual: " + Salud[0] + RESET, 10);
                default:

            }

        } else if (Salud[3] <= 0) {
            EncantamientoEspadaCritico = false;
        }
    }
    private static void EncantamientoEspadaResistencia_Activo(){
        if (EncantamientoEspadaResistencia && Salud[3] > 0){
            PuntosResistenciaEspada++;
            if (PuntosResistenciaEspada >= 4){
                PuntosResistenciaEspada = 0;
                Salud[3]++;
                imprimirPocoAPoco(CYAN + "  --> El encantamiento de resistencia de espada, evita que se desgaste este turno.", 10);
            }
        } else if ( Salud[3] <= 0) {
            EncantamientoEspadaResistencia = false;
        }


    }
    private static void EncantamientoEspadaVelocidad_Activo(){
        VelocidadEspada = false;
        if (EncantamientoEspadaVelocidad && Salud[3] > 0){
            Random random = new Random();
            VelocidadEspada = Math.random() < 0.5;
        } else if (Salud[3] <= 0) {
            EncantamientoEspadaVelocidad = false;
        }
    }


    private static void PociónVida() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[0] > 0) {
            Cantidades[0] -= 1;
            if (MonstruoEnCombate) {
                imprimirPocoAPoco(AZUL + "  ¿Quieres usar la poción en el jugador (1) o en la invocación (2)?" + RESET, 10);
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1":
                        //Nada
                        break;
                    case "2":
                        SaludInvocado += 15;
                        imprimirPocoAPoco(VERDE + "  Poción de curación aplicado +15 de salud." + RESET, 10);
                        if (SaludInvocado > 100){SaludInvocado = 100;  imprimirPocoAPoco(AMARILLO + "  *! La curación tiene un limite. " + CYAN, 15);}
                        imprimirPocoAPoco(MAGENTA + "  * Salud de " + NombreInvocado + " actual: " + SaludInvocado + RESET, 10);

                        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                        scanner.nextLine();
                        return;
                    default:
                        imprimirPocoAPoco(ROJO + "  Opción inválida, la poción se cae y no causa ningún efecto." + RESET, 10);
                        scanner.nextLine();

                        return;
                }
            }


            Salud[0] += 25;
            imprimirPocoAPoco(VERDE + "  Poción de curación aplicado +25 de salud." + RESET, 10);
            if (Salud[0] > SaludMax[0]) {
                Salud[0] = SaludMax[0];
                imprimirPocoAPoco(CYAN + "  La poción de curación supera el límite de la salud máxima del jugador.", 10);
                imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
                System.out.println("");
            }
            imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);
        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna poción de curación." + RESET, 10);
        }

        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void PociónFuerza() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[1] > 0) {
            Cantidades[1] -= 1;
            if (MonstruoEnCombate) {
                imprimirPocoAPoco(AZUL + "  ¿Quieres usar la poción en el jugador (1) o en la invocación (2)?" + RESET, 10);
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1":
                        //Nada
                        break;
                    case "2":
                        ContadorFuerza[1] = 6;
                        Daño[3] = 3;
                        imprimirPocoAPoco(VERDE + "  Poción de fuerza aplicado a invocación +3 de daño." + RESET, 10);
                        imprimirPocoAPoco(MAGENTA + "  * Daño de poción actual de invocación: " + Daño[3] + RESET, 10);

                        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                        scanner.nextLine();
                        return;
                    default:
                        imprimirPocoAPoco(ROJO + "  Opción inválida, la poción se cae y no causa ningún efecto." + RESET, 10);
                        scanner.nextLine();

                        return;
                }
            }

            ContadorFuerza[0] = 6;
            Daño[2] = 4;
            imprimirPocoAPoco(VERDE + "  Poción de fuerza aplicado +4 de daño." + RESET, 10);
            imprimirPocoAPoco(MAGENTA + "  * Daño de poción actual: " + Daño[2] + RESET, 10);
        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna poción de fuerza." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void PociónAgilidad() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[2] > 0) {
            Cantidades[2]  -= 1;
            if (MonstruoEnCombate) {
                imprimirPocoAPoco(AZUL + "  ¿Quieres usar la poción en el jugador (1) o en la invocación (2)?" + RESET, 10);
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1":
                        //Nada
                        break;
                    case "2":
                        ContadorAgilidad[1] = 5;
                        Agilidad[6] = 3;
                        imprimirPocoAPoco(VERDE + "  Poción de agilidad aplicado a invocación +3 de agilidad." + RESET, 10);
                        imprimirPocoAPoco(MAGENTA + "  * Agilidad de poción actual de invocación: " + Agilidad[6] + RESET, 10);

                        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                        scanner.nextLine();
                        return;
                    default:
                        imprimirPocoAPoco(ROJO + "  Opción inválida, la poción se cae y no causa ningún efecto." + RESET, 10);
                        scanner.nextLine();

                        return;
                }
            }


            ContadorAgilidad[0] = 6;
            Agilidad[1] = 3;
            imprimirPocoAPoco(VERDE + "  Poción de agilidad aplicado +3 de agilidad." + RESET, 10);
            imprimirPocoAPoco(MAGENTA + "  * Agilidad de poción actual: " + Agilidad[1] + RESET, 10);
        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna poción de agilidad." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void Antorchas() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[4] > 0) {
            Cantidades[4]  -= 1;
            ContadorAgilidad[3] = 6;
            Agilidad[4] += 1;
            imprimirPocoAPoco(VERDE + "  Agilidad aplicado por antorcha +1" + RESET, 10);
            imprimirPocoAPoco(MAGENTA + "  * Agilidad por antorcha: " + Agilidad[4] + RESET, 10);
        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna antorcha." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void CristalEnergia() {
        Scanner scanner = new Scanner(System.in);

        if (Cantidades[5] > 0) {
            VerificacionVaritas();
            Cantidades[5] -= 1;
            imprimirPocoAPoco(MAGENTA + "  Tu cristal de mana brilla intensamente." + RESET, 10);
            PiedraRecargarVarita();
        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ningun cristal de mana." + RESET, 10);
        }
        varitasPoseidas.clear();
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void Bayas() {
        Scanner scanner = new Scanner(System.in);

        if (Cantidades[6] > 0) {
            Cantidades[6] -= 1;
            Salud[0] += 5;
            if (Salud[0] > SaludMax[0]) {
                Salud[0] = SaludMax[0];
                imprimirPocoAPoco(CYAN + "  Las bayas te curan un poco y supera el límite de la salud máxima del jugador.", 10);
                imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
                System.out.println("");
            }
            imprimirPocoAPoco(VERDE + "  Las bayas te curan, aplicado +5 de salud al jugador." + RESET, 10);
            imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);

        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna baya para comer." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void PociónResistencia() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[7] > 0) {
            Cantidades[7]  -= 1;
            if (MonstruoEnCombate) {
                imprimirPocoAPoco(AZUL + "  ¿Quieres usar la poción en el jugador (1) o en la invocación (2)?" + RESET, 10);
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1":
                        //Nada
                        break;
                    case "2":
                        ContadorResistencia[1] = 6;
                        imprimirPocoAPoco(VERDE + "  El daño directo infligido a invocación se reduce considerablemente." + RESET, 10);
                        imprimirPocoAPoco(MAGENTA + "  * Reducción de daño por poción a invocación: -20%"  + RESET, 10);

                        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                        scanner.nextLine();
                        return;
                    default:
                        imprimirPocoAPoco(ROJO + "  Opción inválida, la poción se cae y no causa ningún efecto." + RESET, 10);
                        scanner.nextLine();

                        return;
                }
            }


            ContadorResistencia[0] = 6;
            imprimirPocoAPoco(VERDE + "  Una poción que fortalece el cuerpo del usuario, reduciendo el daño de ataques físicos y mágicos.", 10);
            imprimirPocoAPoco(MAGENTA + "  * Daño físico: -20%; Daño mágico: -50%"  + RESET, 10);
        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna poción de resistencia." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void PociónVelocidad() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[8] > 0) {
            Cantidades[8]  -= 1;
            if (MonstruoEnCombate) {
                imprimirPocoAPoco(AZUL + "  ¿Quieres usar la poción en el jugador (1) o en la invocación (2)?" + RESET, 10);
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1":
                        //Nada
                        break;
                    case "2":
                        ContadorVelocidad[1] = 3;
                        imprimirPocoAPoco(VERDE + "  La velocidad de ataque de la invocación aumenta." + RESET, 10);
                        imprimirPocoAPoco(MAGENTA + "  * Velocidad de ataque de invocación: x2"+ RESET, 10);

                        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                        scanner.nextLine();
                        return;
                    default:
                        imprimirPocoAPoco(ROJO + "  Opción inválida, la poción se cae y no causa ningún efecto." + RESET, 10);
                        scanner.nextLine();

                        return;
                }
            }

            ContadorVelocidad[0] = 3;

            imprimirPocoAPoco(VERDE + "  Tu velocidad de ataque se duplica" + RESET, 10);
            imprimirPocoAPoco(MAGENTA + "  * Velocidad de ataque: x2"+ RESET, 10);
        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna poción de velocidad." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void PociónDestreza() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[9] > 0) {
            Cantidades[9]  -= 1;
            if (MonstruoEnCombate) {
                imprimirPocoAPoco(AZUL + "  ¿Quieres usar la poción en el jugador (1) o en la invocación (2)?" + RESET, 10);
                String opcion = scanner.nextLine().trim();
                switch (opcion) {
                    case "1":
                        //Nada
                        break;
                    case "2":
                        ContadorDestreza[1] = 6;

                        imprimirPocoAPoco(MAGENTA + "  Los ataques de la invocación, ahora realizaran el daño máximo" + RESET, 10);

                        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                        scanner.nextLine();
                        return;
                    default:
                        imprimirPocoAPoco(ROJO + "  Opción inválida, la poción se cae y no causa ningún efecto." + RESET, 10);
                        scanner.nextLine();

                        return;
                }
            }



            ContadorDestreza[0] = 6;

            imprimirPocoAPoco(MAGENTA + "  Tus ataques ahora realizaran el daño máximo" + RESET, 10);

        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna poción de destreza." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void PociónConcentración() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[10] > 0) {
            Cantidades[10]  -= 1;
            ContadorConcentración = 6;
            imprimirPocoAPoco(VERDE + "  Tu cuerpo almacena algo de mana natural." + RESET, 10);
            imprimirPocoAPoco(VERDE + "  La poción te ayuda a concentrar ese mana hacia tus varitas y recargarlas más rapido." + RESET, 10);
            imprimirPocoAPoco(MAGENTA + "  * Velocidad de recarga de varitas: x2"+ RESET, 10);
        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna poción de concentración." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void FrascoTormenta() {
        Scanner scanner = new Scanner(System.in);
        if (Salud[0] <= 30){
            imprimirPocoAPoco(AMARILLO + "  ¿Seguro de querer usar hechizo de tormenta dentro de un frasco?", 10);
            imprimirPocoAPoco("  Probabilidad 33% de daño a jugador", 10);
            imprimirPocoAPoco(ROJO + "  * Salud de jugador baja: " + Salud[0] + RESET,10);
            imprimirPocoAPoco(AMARILLO + "  (1.Si) (2.No)" + RESET, 10);
            int opcion = scanner.nextInt();
            if (opcion == 1) {
                if (Cantidades[11] > 0) {
                    Cantidades[11] -= 1;

                    imprimirPocoAPoco(AMARILLO + "  Un hechizo de tormenta dentro de un frasco.", 10);
                    imprimirPocoAPoco("  El hechizo es liberado y daña al enemigo gravemente.", 10);
                    if (ContadorResistencia[2] > 0){
                        SaludEnemigo -= 20;
                        imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: -50%", 10);
                        imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo por hechizo de tormenta: 20" + RESET, 10);
                    }
                    else {
                        SaludEnemigo -= 40;
                        imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo por hechizo de tormenta: 40" + RESET, 10);
                    }

                    imprimirPocoAPoco(VERDE + "  * Salud del enemigo actual: " + SaludEnemigo + RESET, 10);

                    if (MonstruoEnCombate){
                        int DañoColateralInvocacionRandom = (int) (Math.random() * 10) + 1;
                        if (DañoColateralInvocacionRandom == 10 || DañoColateralInvocacionRandom == 1 || DañoColateralInvocacionRandom == 5) {
                            imprimirPocoAPoco(AMARILLO + "  La tormenta es liberada y se sale de control.", 10);
                            if (ContadorResistencia[1] > 0){
                                SaludInvocado -= 10;
                                imprimirPocoAPoco(VERDE + "  {**} Daño reducido, por poción de resistencia: -50%", 10);
                                imprimirPocoAPoco(AMARILLO + "  * Daño a " + NombreInvocado + " por hechizo de tormenta: 10" + RESET, 10);
                            }
                            else {
                                SaludInvocado -= 20;
                                imprimirPocoAPoco(AMARILLO + "  * Daño a " + NombreInvocado + " por hechizo de tormenta: 20" + RESET, 10);
                            }

                            imprimirPocoAPoco(VERDE + "  * Salud de la invocación actual: " + SaludInvocado + RESET, 10);
                            if (SaludInvocado<= 0) {
                                MonstruoEnCombate = false;
                                imprimirPocoAPoco(ROJO + "  ¡La invocación es derrotada por la tormenta y se retira del combate!" + RESET, 10);

                            }
                        }

                    }
                    int DañoColateralRandom = (int) (Math.random() * 10) + 1;
                    if (DañoColateralRandom == 10 || DañoColateralRandom == 1 || DañoColateralRandom == 5) {
                        imprimirPocoAPoco(AMARILLO + "  La tormenta es liberada y se sale de control.", 10);
                        if (ContadorResistencia[0] > 0){

                            if (Salud[4] > 0){
                                Salud[4] -= 20;
                                imprimirPocoAPoco(CYAN + "  -> El escudo mágico, recibe el daño: 20", 10);
                                if (Salud[4] < 0){Salud[4] = 0;
                                    imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                                }
                                else {
                                    imprimirPocoAPoco("  * Durabilidad del escudo mágico actual: " + Salud[4] + RESET, 15);
                                }

                            }else{
                                Salud[0] -= 10;
                                imprimirPocoAPoco(VERDE + "  {**} Daño reducido, por poción de resistencia: -50%", 10);
                                imprimirPocoAPoco(ROJO + "  * Daño a jugador por hechizo de tormenta: 10" + RESET, 10);
                            }
                        }
                        else {
                            if (Salud[4] > 0){
                                Salud[4] -= 20;
                                imprimirPocoAPoco(CYAN + "  -> El escudo mágico, recibe el daño: 20", 10);
                                if (Salud[4] < 0){Salud[4] = 0;
                                    imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                                }
                                else {
                                    imprimirPocoAPoco("  * Durabilidad del escudo mágico actual: " + Salud[4] + RESET, 15);
                                }

                            }else{
                                Salud[0] -= 20;
                                imprimirPocoAPoco(ROJO + "  * Daño a jugador por hechizo de tormenta: 20" + RESET, 10);
                            }
                        }


                        imprimirPocoAPoco(VERDE + "  * Salud del jugador actual: " + Salud[0] + RESET, 10);
                        if (Salud[0] <= 0) {
                            imprimirPocoAPoco(ROJO + "  ¡Has muerto, por frasco de tormenta!" + RESET, 10);
                            imprimirPocoAPoco("  El programa se cerrara en breve", 2);
                            imprimirPocoAPoco("  . . . . . . . . . . . . . . . .", 500);
                            System.exit(0);
                        }

                    }

                }
                else {
                    imprimirPocoAPoco("  ...", 300);
                    imprimirPocoAPoco(ROJO + "  No posees ninguna frasco de tormenta." + RESET, 10);
                }
            }
            else {        imprimirPocoAPoco(VERDE + "  * Decides no arriesgarte, ¡Buena suerte!" + RESET, 10);}
        }
        else {
            if (Cantidades[11] > 0) {
                Cantidades[11] -= 1;
                imprimirPocoAPoco(VERDE + "  Un hechizo de tormenta dentro de un frasco." + RESET, 10);
                imprimirPocoAPoco(VERDE + "  El hechizo es liberado y daña al enemigo gravemente." + RESET, 10);
                if (ContadorResistencia[2] > 0){
                    SaludEnemigo -= 20;
                    imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: -50%", 10);
                    imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo por hechizo de tormenta: 20" + RESET, 10);
                }
                else {
                    SaludEnemigo -= 40;
                    imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo por hechizo de tormenta: 40" + RESET, 10);
                }
                imprimirPocoAPoco(VERDE + "  * Salud del enemigo actual: " + SaludEnemigo + RESET, 10);

                if (MonstruoEnCombate){
                    int DañoColateralInvocacionRandom = (int) (Math.random() * 10) + 1;
                    if (DañoColateralInvocacionRandom == 10 || DañoColateralInvocacionRandom == 1 || DañoColateralInvocacionRandom == 5) {
                        imprimirPocoAPoco(AMARILLO + "  La tormenta es liberada y se sale de control.", 10);
                        if (ContadorResistencia[1] > 0){
                            SaludInvocado -= 10;
                            imprimirPocoAPoco(VERDE + "  {**} Daño reducido, por poción de resistencia: -50%", 10);
                            imprimirPocoAPoco(AMARILLO + "  * Daño a " + NombreInvocado + " por hechizo de tormenta: 10" + RESET, 10);
                        }
                        else {
                            SaludInvocado -= 20;
                            imprimirPocoAPoco(AMARILLO + "  * Daño a " + NombreInvocado + " por hechizo de tormenta: 20" + RESET, 10);
                        }

                        imprimirPocoAPoco(VERDE + "  * Salud de la invocación actual: " + SaludInvocado + RESET, 10);
                        if (SaludInvocado<= 0) {
                            MonstruoEnCombate = false;
                            imprimirPocoAPoco(ROJO + "  ¡La invocación es derrotada por la tormenta y se retira del combate!" + RESET, 10);

                        }
                    }

                }

                int DañoColateralRandom = (int) (Math.random() * 10) + 1;
                if (DañoColateralRandom == 10 || DañoColateralRandom == 1 || DañoColateralRandom == 5) {
                    imprimirPocoAPoco(AMARILLO + "  La tormenta es liberada y se sale de control.", 10);
                    if (ContadorResistencia[0] > 0){
                        if (Salud[4] > 0){
                            Salud[4] -= 20;
                            imprimirPocoAPoco(CYAN + "  -> El escudo mágico, recibe el daño: 20", 10);
                            if (Salud[4] < 0){Salud[4] = 0;
                                imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                            }
                            else {
                                imprimirPocoAPoco("  * Durabilidad del escudo mágico actual: " + Salud[4] + RESET, 15);
                            }

                        }else{
                            Salud[0] -= 10;
                            imprimirPocoAPoco(VERDE + "  {**} Daño reducido, por poción de resistencia: -50%", 10);
                            imprimirPocoAPoco(ROJO + "  * Daño a jugador por hechizo de tormenta: 10" + RESET, 10);
                        }
                    }
                    else {
                        if (Salud[4] > 0){
                            Salud[4] -= 20;
                            imprimirPocoAPoco(CYAN + "  -> El escudo mágico, recibe el daño: 20", 10);
                            if (Salud[4] < 0){Salud[4] = 0;
                                imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                            }
                            else {
                                imprimirPocoAPoco("  * Durabilidad del escudo mágico actual: " + Salud[4] + RESET, 15);
                            }

                        }else{
                            Salud[0] -= 20;
                            imprimirPocoAPoco(ROJO + "  * Daño a jugador por hechizo de tormenta: 20" + RESET, 10);
                        }
                    }

                    imprimirPocoAPoco(VERDE + "  * Salud del jugador actual: " + Salud[0] + RESET, 10);
                }


            } else {
                imprimirPocoAPoco("  ...", 300);
                imprimirPocoAPoco(ROJO + "  No posees ninguna frasco de tormenta." + RESET, 10);
            }
        }

        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void FrascoVeneno() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[12] > 0) {
            Cantidades[12]  -= 1;
            if (MonstruoEnCombate) {
                imprimirPocoAPoco(AZUL + "  ¿Quieres usar la poción en el enemigo (1) o en la invocación (2)?" + RESET, 10);
                String opcion = scanner.nextLine().trim();
                imprimirPocoAPoco(VERDE + "  Un frasco con un veneno peligroso, que inflije daño con el tiempo." + RESET, 10);
                switch (opcion) {
                    case "1":
                        //Nada
                        break;
                    case "2":
                        imprimirPocoAPoco(VERDE + "  Le arrojas el frasco a la invocación." + RESET, 10);
                        if (InvocaciónSombTox){
                            imprimirPocoAPoco(VERDE + "  * La invocación es inmune al veneno, por lo que no le hace nada." + RESET, 10);
                        }else {
                            ContadorVeneno[1] += 7;
                        }
                        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                        scanner.nextLine();
                        return;
                    default:
                        imprimirPocoAPoco(ROJO + "  Opción inválida, la poción se cae y no causa ningún efecto." + RESET, 10);
                        scanner.nextLine();

                        return;
                }
            }

            ContadorVeneno[0] += 7;

            imprimirPocoAPoco(VERDE + "  Le arrojas el frasco al enemigo." + RESET, 10);

        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ningun frasco con veneno." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }
    private static void StilusArcano(){

        if (Cantidades[13] > 0){
            Cantidades[13]--;

            Random random = new Random();
            int opcion = random.nextInt(4); // Genera un número aleatorio entre 0 y 3 (4 opciones)
            int opcion2 = opcion;
            switch (opcion2) {
                case 0:
                    if (!EncantamientoArmaduraEspinas){
                    EncantamientoArmaduraEspinas = true;
                    imprimirPocoAPoco(MAGENTA + "  ->> Tu armadura es imbuida con el encantamiento de espinas." + RESET, 10);
                    Salud[1]+=5;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la resistencia de tu armadura +5" + RESET, 10);
                    }else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento espinas otorgado, ya lo poseia la armadura." + RESET, 10);
                    }
                    break;

                case 1:
                    if (!EncantamientoArmaduraReparación){
                    EncantamientoArmaduraReparación = true;
                    imprimirPocoAPoco(MAGENTA + "  ->> Tu armadura es imbuida con el encantamiento de reparación." + RESET, 10);
                        Salud[1]+=5;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la resistencia de tu armadura +5" + RESET, 10);
                    }else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento reparación otorgado, ya lo poseia la armadura." + RESET, 10);
                    }
                    break;
                case 2:
                    if (!EncantamientoArmaduraAgilidad) {
                        EncantamientoArmaduraAgilidad = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu armadura es imbuida con el encantamiento de agilidad." + RESET, 10);
                        Salud[1]+=5;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la resistencia de tu armadura +5" + RESET, 10);
                    }else {
                    imprimirPocoAPoco(AMARILLO + "  !* El encantamiento agilidad otorgado, ya lo poseia la armadura." + RESET, 10);
                }
                    break;
                case 3:
                    if (!EncantamientoArmaduraRecarga) {
                        EncantamientoArmaduraRecarga = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu armadura es imbuida con el encantamiento de recarga." + RESET, 10);
                        Salud[1]+=5;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la resistencia de tu armadura +5" + RESET, 10);
                    }else {
                    imprimirPocoAPoco(AMARILLO + "  !* El encantamiento recarga otorgado, ya lo poseia la armadura." + RESET, 10);
                }
                    break;
                default:
                    break;
            }


        }else if (Cantidades[13] == 0){
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ningún stilus arcano para usar." + RESET, 10);
        }else if (Salud[2] == 0) {
            imprimirPocoAPoco("  ...", 300);
            Cantidades[13]++;
            imprimirPocoAPoco(ROJO + "  No posees ninguna armadura para encantar." + RESET, 10);
        }else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No se que paso." + RESET, 10);
        }

    }
    private static void RunaDeLosAntigios(){

        if (Cantidades[14] > 0){
            Cantidades[14]--;

            imprimirPocoAPoco("  ", 10);

            Random random = new Random();
            int opcion = random.nextInt(4); // Genera un número aleatorio entre 0 y 3 (4 opciones)
            int opcion2 = opcion;
            switch (opcion2) {
                case 0:
                    if (!EncantamientoEscudoEspinas) {
                        EncantamientoEscudoEspinas = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu escudo esta imbuido con el encantamiento de espinas." + RESET, 10);
                        Salud[2]+=3;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la resistencia de tu escudo +3" + RESET, 10);
                    } else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento espinas otorgado, ya lo poseia el escudo." + RESET, 10);
                    }
                    break;
                case 1:
                    if (!EncantamientoEscudoBloqueo) {
                        EncantamientoEscudoBloqueo = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu escudo esta imbuido con el encantamiento de bloqueo." + RESET, 10);
                        Salud[2]+=3;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la resistencia de tu escudo +3" + RESET, 10);
                    } else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento bloqueo otorgado, ya lo poseia el escudo." + RESET, 10);
                    }
                    break;
                case 2:
                    if (!EncantamientoEscudoResistencia) {
                        EncantamientoEscudoResistencia = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu escudo esta imbuido con el encantamiento de resistencia." + RESET, 10);
                        Salud[2]+=3;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la resistencia de tu escudo +3" + RESET, 10);
                    } else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento resistencia otorgado, ya lo poseia el escudo." + RESET, 10);
                    }
                    break;
                case 3:
                    if (!EncantamientoEscudoParalisis) {
                        EncantamientoEscudoParalisis = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu escudo esta imbuido con el encantamiento de paralisis." + RESET, 10);
                        Salud[2]+=3;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la resistencia de tu escudo +3" + RESET, 10);
                    } else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento paralisis otorgado, ya lo poseia el escudo." + RESET, 10);
                    }
                    break;
                default:
                    break;
            }

        }else if (Cantidades[14] == 0){
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna runa de los antiguos para usar." + RESET, 10);
        }else if (Salud[2] == 0) {
            imprimirPocoAPoco("  ...", 300);
            Cantidades[14]++;
            imprimirPocoAPoco(ROJO + "  No posees ningú escudo para encantar." + RESET, 10);
        }else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No se que paso." + RESET, 10);
        }


    }
    private static void PergaminoDelArcanum(){

        if (Cantidades[15] > 0){
            Cantidades[15]--;


            Random random = new Random();
            int opcion = random.nextInt(4); // Genera un número aleatorio entre 0 y 3 (4 opciones)
            int opcion2 = opcion;
            switch (opcion2) {
                case 0:
                    if (!EncantamientoEspadaCritico) {
                        EncantamientoEspadaCritico = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu espada esta imbuida con el encantamiento de critico." + RESET, 10);
                        Salud[3]+=2;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la durabilidad de tu espada +2" + RESET, 10);
                    } else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento critico otorgado, ya lo poseia la espada." + RESET, 10);
                    }
                    break;
                case 1:
                    if (!EncantamientoEspadaCurativo) {
                        EncantamientoEspadaCurativo = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu espada esta imbuida con el encantamiento curativo." + RESET, 10);
                        Salud[3]+=2;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la durabilidad de tu espada +2" + RESET, 10);
                    } else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento curativo otorgado, ya lo poseia la espada." + RESET, 10);
                    }
                    break;
                case 2:
                    if (!EncantamientoEspadaResistencia) {
                        EncantamientoEspadaResistencia = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu espada esta imbuida con el encantamiento de resistencia." + RESET, 10);
                        Salud[3]+=2;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la durabilidad de tu espada +2" + RESET, 10);
                    } else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento resistencia otorgado, ya lo poseia la espada." + RESET, 10);
                    }
                    break;
                case 3:
                    if (!EncantamientoEspadaVelocidad) {
                        EncantamientoEspadaVelocidad = true;
                        imprimirPocoAPoco(MAGENTA + "  ->> Tu espada esta imbuida con el encantamiento de velocidad." + RESET, 10);
                        Salud[3]+=2;
                        imprimirPocoAPoco(MAGENTA + "  ->> El encantamiento aumenta la durabilidad de tu espada +2" + RESET, 10);
                    } else {
                        imprimirPocoAPoco(AMARILLO + "  !* El encantamiento velocidad otorgado, ya lo poseia la espada." + RESET, 10);
                    }
                    break;
                default:
                    break;
            }

        }else if (Cantidades[15] == 0){
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ningún pergamino del arcanum para usar." + RESET, 10);
        } else if (Salud[3] == 0) {
            imprimirPocoAPoco("  ...", 300);
            Cantidades[15]++;
            imprimirPocoAPoco(ROJO + "  No posees ninguna espadada para encantar." + RESET, 10);
        }else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No se que paso." + RESET, 10);
        }

    }
    private static void KitReparación() {
        if (Cantidades[16] > 0) {

            List<Integer> opciones = new ArrayList<>();
            if (Salud[1] > 0) opciones.add(1);
            if (Salud[2] > 0) opciones.add(2);
            if (Salud[3] > 0) opciones.add(3);

            if (opciones.isEmpty()) {
                imprimirPocoAPoco(ROJO + "  No hay equipamientos reparables." + RESET, 10);
                return;
            }

            imprimirPocoAPoco("-------------------------------------------------------------------------------------", 2);
            imprimirPocoAPoco(AMARILLO + "  Equipamiento reparable: " + RESET, 25);

            int opcionActual = 1;
            for (int opcion : opciones) {
                switch (opcion) {
                    case 1 -> imprimirPocoAPoco("  " + opcionActual + ". Armadura (Durabilidad: " + Salud[1] + ")", 10);
                    case 2 -> imprimirPocoAPoco("  " + opcionActual + ". Escudo (Durabilidad: " + Salud[2] + ")", 10);
                    case 3 -> imprimirPocoAPoco("  " + opcionActual + ". Espada (Durabilidad: " + Salud[3] + ")", 10);
                }
                opcionActual++;
            }

            imprimirPocoAPoco("-------------------------------------------------------------------------------------", 2);
            imprimirPocoAPoco(AZUL + "  Escoge una de las opciones anteriores (0 para salir). " + RESET, 10);

            Scanner scanner = new Scanner(System.in);
            int seleccion = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after nextInt()

            if (seleccion == 0) {
                //imprimirPocoAPoco(AMARILLO + "  ¡Buena suerte!" + AZUL + " (Enter para continuar)" + RESET, 10);
                //scanner.nextLine();
                return;
            }

            int opcionReal = -1;
            opcionActual = 1;
            for (int opcion : opciones) {
                if (opcionActual == seleccion) {
                    opcionReal = opcion;
                    break;
                }
                opcionActual++;
            }

            if (opcionReal != -1) {
                switch (opcionReal) {
                    case 1:
                        if (ContadorReparaciónArmadura >= 2) {
                            imprimirPocoAPoco(ROJO + "  La Armadura ya no puede ser reparada más veces." + RESET, 10);
                        } else {
                            Salud[1] += 20;
                            Cantidades[16]--;
                            ContadorReparaciónArmadura++;
                            imprimirPocoAPoco("  La armadura ha sido reparada y su durabilidad aumentó en +20.", 10);
                            imprimirPocoAPoco(VERDE + "  * Durabilidad de armadura actual: " + Salud[1] + RESET, 10);
                        }
                        break;
                    case 2:
                        if (ContadorReparaciónEscudo >= 2) {
                            imprimirPocoAPoco(ROJO + "  El Escudo ya no puede ser reparado más veces." + RESET, 10);
                        } else {
                            Salud[2] += 10;
                            Cantidades[16]--;
                            ContadorReparaciónEscudo++;
                            imprimirPocoAPoco("  El escudo ha sido reparado y su durabilidad aumentó en +10.", 10);
                            imprimirPocoAPoco(VERDE + "  * Durabilidad de escudo actual: " + Salud[2] + RESET, 10);
                        }
                        break;
                    case 3:
                        if (ContadorReparaciónEspada >= 2) {
                            imprimirPocoAPoco(ROJO + "  La Espada ya no puede ser reparada más veces." + RESET, 10);
                        } else {
                            Salud[3] += 8;
                            Cantidades[16]--;
                            ContadorReparaciónEspada++;
                            imprimirPocoAPoco("  La espada ha sido reparada y su durabilidad aumentó en +8.", 10);
                            imprimirPocoAPoco(VERDE + "  * Durabilidad de espada actual: " + Salud[3] + RESET, 10);
                        }
                        break;
                    default:
                        imprimirPocoAPoco(ROJO + "  Selección inválida." + RESET, 10);
                }
            } else {
                imprimirPocoAPoco(ROJO + "  Selección inválida." + RESET, 10);
            }

        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ningún Kit de reparación para usar." + RESET, 10);
            return;
        }


    }
    private static void PergaminoInvocacion() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[17] > 0) {
            Cantidades[17]--;
            imprimirPocoAPoco(MAGENTA + "  Has invocado a Guerrero Eterno." + RESET, 10);
            MonstruoEnCombate = true;

            NivelInvocado = NivelMonstruo[11];
            NombreInvocado = NombreMonstruo[11];
            SaludInvocado = SaludMonstruo[11];
            DañoMaxInvocacion = DañoMonstruo[11];
            DañoMinInvocacion = DañoMaxInvocacion - 11;
            AgilidadInvocado = AgilidadMoustro[11];
            CuraciónInvocado = CuraciónMonstruo[11];
            ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(11) + 0.2;
            imprimirPocoAPoco("---------------------------------------------------", 3);
            imprimirPocoAPoco(AMARILLO + "  Atributos de invocación:" + RESET, 10);
            imprimirPocoAPoco("  * Nivel de invocación: " + NivelInvocado, 7);
            imprimirPocoAPoco("  * Salud de invocación: " + SaludInvocado, 7);
            imprimirPocoAPoco("  * Daño de invocación: " + DañoMinInvocacion + " - " + DañoMaxInvocacion, 7);
            imprimirPocoAPoco("  * Agilidad de invocación: " + AgilidadInvocado, 7);
            imprimirPocoAPoco("  * Curación de invocación: " + CuraciónInvocado, 7);
            imprimirPocoAPoco("---------------------------------------------------", 3);
            imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
            scanner.nextLine();
            System.out.println("");
        }
        else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ningun pergamino para invocar." + RESET, 10);
        }
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();

    }
    private static void PociónCuracionGrande() {
        Scanner scanner = new Scanner(System.in);
        if (Cantidades[18] > 0) {
            Cantidades[18] -= 1;
            int saludrecuperada = (SaludMax[0] - Salud[0]);
            Salud[0] = SaludMax[0];

            imprimirPocoAPoco(VERDE + "  La poción de curación grande restaura toda tu salud." + RESET, 10);
            imprimirPocoAPoco(VERDE + "  Salud recuperada: +" + saludrecuperada + RESET, 10);
            imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);

        } else {
            imprimirPocoAPoco("  ...", 300);
            imprimirPocoAPoco(ROJO + "  No posees ninguna poción de curación grande." + RESET, 10);
        }

        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
    }

    private static void UsarConsumibles() {
        Scanner scanner = new Scanner(System.in);
        boolean ContinuarUsarConsumible = true;
        while (ContinuarUsarConsumible) {
            imprimirPocoAPoco("-------------------------------------------------------------------------------------", 2);
            if (modoroot) {
                imprimirPocoAPoco(MAGENTA + "  Atributos del desarrollador " + NombreJugador + ", nivel: "+ NivelJugador + ", exp: "+Experiencia + "/"+ExperienciaMax + RESET, 7);
            } else if (ClaseGuerrero) {
                imprimirPocoAPoco(MAGENTA + "  Atributos del GUERRERO " + NombreJugador  + ", nivel: "+ NivelJugador +   ", exp: "+Experiencia + "/"+ExperienciaMax + RESET, 7);
            } else if (ClaseMago) {
                imprimirPocoAPoco(MAGENTA + "  Atributos del MAGO " + NombreJugador + ", nivel: "+ NivelJugador +  ", exp: "+Experiencia + "/"+ExperienciaMax + RESET, 7);
            } else if (ClaseInvocador) {
                imprimirPocoAPoco(MAGENTA + "  Atributos del INVOCADOR " + NombreJugador + ", nivel: "+ NivelJugador +  ", exp: "+Experiencia + "/"+ExperienciaMax + RESET, 7);
            }
            imprimirPocoAPoco("  * Salud del jugador: " + Salud[0] + ", máx: " +  SaludMax[0], 5);
            imprimirPocoAPoco("  * Agilidad total del jugador: " + (Agilidad[0] + Agilidad[1] + Agilidad[4] + AgilidadBaston + Agilidad[4]) + BLANCO + " - Base + poción + antorcha + Baston(Clase Mago) + Encantamiento." + RESET, 3);
            imprimirPocoAPoco("  * Daño total del jugador: " + DañoMinJugador + " - " + DañoMaxJugador + BLANCO + " - Base + Espada + Poción + Baston(Clase Mago)." + RESET, 3);
            System.out.println("");


            imprimirPocoAPoco(AMARILLO + "  Equipamiento: " + RESET, 10);
            for (int i = 1; i < Salud.length; i++) {
                if (Salud[i] > 0) {
                    imprimirPocoAPoco("  * Durabilidad de " + Equipamiento[i] + ": " + Salud[i], 7);
                }

            }
            System.out.println("");

            imprimirPocoAPoco(AMARILLO + "  Inventario: " + RESET, 10);
            int opcionActual = 1;
            for (int i = 0; i < Objetos.length; i++) {
                if (Cantidades[i] > 0 && i != 3) { // Excluir el objeto en Cantidades[3]
                    imprimirPocoAPoco("  " + opcionActual + ". " + Objetos[i] + ": " + Cantidades[i], 7);
                    opcionActual++;
                }
            }
            System.out.println("");
            imprimirPocoAPoco("-------------------------------------------------------------------------------------", 2);

            imprimirPocoAPoco(AZUL + "  Escoge una de las opciones anteriores (0 para salir). " + RESET, 10);
            int opcion2 = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after nextInt()

            if (opcion2 == 0) {
                //imprimirPocoAPoco(AMARILLO + "  ¡Buena suerte!" + AZUL + " (Enter para continuar)" + RESET, 15);
                //scanner.nextLine();
                return;
            }

            int opcionReal = -1;
            opcionActual = 1;

            for (int i = 0; i < Objetos.length; i++) {
                if (Cantidades[i] > 0 && i != 3) {
                    if (opcionActual == opcion2) {
                        opcionReal = i;
                        break;
                    }
                    opcionActual++;
                }
            }

            if (opcionReal != -1) {
                switch (opcionReal) {
                    case 0:
                        PociónVida();
                        break;
                    case 1:
                        PociónFuerza();
                        break;
                    case 2:
                        PociónAgilidad();
                        break;
                    case 4:
                        Antorchas();
                        break;
                    case 5:
                        CristalEnergia();
                        break;
                    case 6:
                        Bayas();
                        break;
                    case 7:
                        PociónResistencia();
                        break;
                    case 8:
                        PociónVelocidad();
                        break;
                    case 9:
                        PociónDestreza();
                        break;
                    case 10:
                        PociónConcentración();
                        break;
                    case 11:
                        FrascoTormenta();
                        break;
                    case 12:
                        FrascoVeneno();
                        break;
                    case 13:
                        StilusArcano();
                        break;
                    case 14:
                        RunaDeLosAntigios();
                        break;
                    case 15:
                        PergaminoDelArcanum();
                        break;
                    case 16:
                        KitReparación();
                        break;
                    case 17:
                        PergaminoInvocacion();
                        break;
                    case 18:
                        PociónCuracionGrande();
                        break;
                    default:
                        imprimirPocoAPoco("  Opción no válida.", 10);
                        break;
                }

            } else {
                // En caso de una opción no válida
                imprimirPocoAPoco("  ...", 300);
                imprimirPocoAPoco("  Opción no válida.", 10);
                System.out.println("");
                imprimirPocoAPoco("  Estás tan nervioso en pensar que el enemigo te atacará en cualquier momento.", 10);
                imprimirPocoAPoco("  Que no puedes tomar nada útil de tu inventario.", 10);
                imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                scanner.nextLine();
            }

            if (!EnCombateJugador) {
                System.out.println("");
                imprimirPocoAPoco("  ¿Quieres usar otro objeto de tu inventario?", 10);
                imprimirPocoAPoco(VERDE + "  (1. Si) (2. No)" + RESET, 7);
                int opcionContinuar = scanner.nextInt();
                if (opcionContinuar == 2) {
                    ContinuarUsarConsumible = false;
                }
            }
            else {
                ContinuarUsarConsumible = false;
            }
        }

    }
    private static void EnemigoRetrocede() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        imprimirPocoAPoco(MAGENTA + "  El enemigo parece retroceder, ¿Quieres usar un consumible ahora?" + VERDE + "(1. si) (2. no)" + RESET, 4);
        int opcion = scanner.nextInt();
        System.out.println("");
        if (opcion == 1) {
            UsarConsumibles();
        } else {
            //nada y continua
        }
    }

    private static void Contadores() {
        ContadorTurnos++;
        ContadorOportuno++;
        ContadorVarita++;


        if (ContadorAgilidad[0] > 0) {
            ContadorAgilidad[0]--;
            if (ContadorAgilidad[0] <= 0) {
                imprimirPocoAPoco(BLANCO + "  Tu poción de agilidad dejó de hacer efecto." + RESET, 15);
                Agilidad[1] = 0;
            }

        }
        if (ContadorAgilidad[3] > 0) {
            ContadorAgilidad[3]--;
            if (ContadorAgilidad[3] <= 0) {
                imprimirPocoAPoco(BLANCO + "  Tu antorcha se se vuelve cenizas." + RESET, 15);
                Agilidad[4] = 0;
            }

        }

        if (ContadorFuerza[0] > 0) {
            ContadorFuerza[0]--;
            if (ContadorFuerza[0] <= 0) {
                imprimirPocoAPoco(BLANCO + "  Tu poción de Fuerza dejó de hacer efecto." + RESET, 15);
                Daño[2] = 0;
            }

        }
        if (ContadorResistencia[0] > 0) {//es un contador regresivo
            ContadorResistencia[0]--;
            if (ContadorResistencia[0] <= 0) {
                imprimirPocoAPoco(BLANCO + "  Tu poción de resistencia dejó de hacer efecto." + RESET, 15);
            }
        }

        if (ContadorVelocidad[0] > 0) {
            ContadorVelocidad[0]--;
            if (ContadorVelocidad[0] <= 0) {
                imprimirPocoAPoco(BLANCO + "  Tu poción de velocidad dejó de hacer efecto." + RESET, 15);
            }
        }
        if (ContadorConcentración > 0) {
            ContadorConcentración--;
            if (ContadorConcentración <= 0) {
                imprimirPocoAPoco(BLANCO + "  Tu poción de concentración dejó de hacer efecto." + RESET, 15);
            }
        }
        if (ContadorDestreza[0] > 0) {
            ContadorDestreza[0]--;
            if (ContadorDestreza[0] <= 0) {
                imprimirPocoAPoco(BLANCO + "  Tu poción de destreza dejó de hacer efecto." + RESET, 15);
            }
        }


        if (ContadorOportuno >= 3) {
            EnemigoRetrocede();
            ContadorOportuno = 0;
        }
        if (ContadorVarita >= 2 && ClaseMago == false) {
            elegirVarita();
            ContadorVarita = 0;
        } else if (ClaseMago == true) {
            elegirVarita();
            ContadorVarita = 0;
        }

        if (ClaseGuerrero) {
            ContadorEscudoMagicoGuerrero++;
            if (ContadorEscudoMagicoGuerrero >= 5) {
                ContadorEscudoMagicoGuerrero = 0;
                    Salud[4] += HabilidadEscudoMagicoGuerrero;
                    imprimirPocoAPoco(MAGENTA + "  El guerrero obtiene escudo mágico +" + HabilidadEscudoMagicoGuerrero + RESET, 15);
                    imprimirPocoAPoco(MAGENTA + "  * Durabilidad del escudo mágico: " + Salud[4] + RESET, 15);

            }

        }

    }
    private static void ContadoresEfectoVarita() {
        ContadorAgilidadBaston++;
        ContadorFuerzaBaston++;

        ContadorVHielo++;
        ContadorVDebilidad++;
        ContadorVProtección++;


        if (ContadorAgilidadBaston >= 3 && AgilidadBaston > 0) {
            AgilidadBaston = 0;
            ContadorAgilidadBaston = 0;
            imprimirPocoAPoco(BLANCO + "  La magia de agilidad de de tu bastón dejó de hacer efecto." + RESET, 15);
        }
        if (ContadorFuerzaBaston >= 3 && FuerzaBaston > 0) {
            FuerzaBaston = 0;
            ContadorFuerzaBaston = 0;
            imprimirPocoAPoco(BLANCO + "  La magia de fuerza de de tu bastón dejó de hacer efecto." + RESET, 15);
        }
        if (ContadorVHielo >= 3 && Agilidad[3] > 0) {
            imprimirPocoAPoco(BLANCO + "  La magia ralentizante de tu varita dejó de hacer efecto." + RESET, 15);
            Agilidad[3] = 0;
            ContadorVHielo = 0;

        }
        if (ContadorVDebilidad >= 3 && DañoVarita[3] > 0) {
            imprimirPocoAPoco(BLANCO + "  La magia debilitante de tu varita dejó de hacer efecto." + RESET, 15);
            DañoVarita[3] = 0; //El daño se convierte en - probabilidad golpear a jugador.
            ContadorVDebilidad = 0;

        }
        if (ContadorVProtección >= 1 && Agilidad[2] > 0) { //Cambiar la agilidad para varita tanto arriba como aqui.
            imprimirPocoAPoco(BLANCO + "  La magia protectora de tu varita dejó de hacer efecto." + RESET, 15);
            Agilidad[2] = 0;
            ContadorVProtección = 0;
        }
    }
    private static void ContadoresInvocación() {
        Scanner scanner = new Scanner(System.in);

        if (ContadorAgilidad[1] > 0 ) {
            ContadorAgilidad[1]--;
            if (ContadorAgilidad[1] <= 0) {
                Agilidad[6] = 0;
                imprimirPocoAPoco(BLANCO + "  La poción de agilidad dejó de hacer efecto en la invocación." + RESET, 15);
            }

        }
        if (ContadorFuerza[1] > 0) {
            ContadorFuerza[1]--;
            if (ContadorFuerza[1] <= 0) {
                imprimirPocoAPoco(BLANCO + "  La poción de Fuerza dejó de hacer efecto en la invocación." + RESET, 15);
                Daño[3] = 0;
            }

        }
        if (ContadorResistencia[1] > 0 ) {
            ContadorResistencia[1]--;
            if (ContadorResistencia[1] <= 0) {
                imprimirPocoAPoco(BLANCO + "  La poción de resistencia dejó de hacer efecto en la invocación." + RESET, 15);
                ContadorResistencia[1] = 0;
            }
        }

        if (ContadorVelocidad[1] > 0) {
            ContadorVelocidad[1]--;
            if (ContadorVelocidad[1] <= 0) {
                imprimirPocoAPoco(BLANCO + "  La poción de velocidad dejó de hacer efecto en la invocación." + RESET, 15);
            }
        }
        if (ContadorDestreza[1] > 0) {
            ContadorDestreza[1]--;
            if (ContadorDestreza[1] <= 0) {
                imprimirPocoAPoco(BLANCO + "  La poción de destreza dejó de hacer efecto en la invocación." + RESET, 15);
            }
        }

        if (ContadorVeneno[1] > 0) {
            // Aplicar daño por veneno
            SaludInvocado -= ContadorVeneno[1];
            imprimirPocoAPoco(VERDE + "  * Daño a " + NombreInvocado + " por veneno: " + ContadorVeneno[1] + ", Salud de invocación actual: " + SaludInvocado + RESET, 15);
            imprimirPocoAPoco(BLANCO + "  Turnos de veneno en enemigo restantes: " + ContadorVeneno[1] + RESET, 15);
            ContadorVeneno[1]--;

            // Comprobar si la invocación se debilita por el veneno
            if (SaludInvocado <= 0 && MonstruoEnCombate) {
                MonstruoEnCombate = false;
                ContadorVeneno[1] = 0;
                imprimirPocoAPoco(ROJO + "  ¡La invocación se debilita por el veneno y se retira del combate!" + RESET, 15);
                System.out.println("");
                // Generar un número aleatorio para la invocación especial
                boolean RandomInvocacionEspecial = (Math.random()) > 0.39;
                if (RandomInvocacionEspecial) {

                    imprimirPocoAPoco(VERDE + "  ¡La magia de la invocación se mezcla con el veneno!" + RESET, 15);
                    imprimirPocoAPoco(VERDE + "  -> Obtienes una nueva invocación: \"Sombra Tóxica\"" + RESET, 15);
                    monstruosInvocados.add(10);
                    System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                    scanner.nextLine();
                    SombraToxica();
                }
            }

            // Verificar si el veneno pierde efecto
            if (ContadorVeneno[1] <= 0) {
                ContadorVeneno[1] = 0;
                imprimirPocoAPoco(BLANCO + "  La alteración de estado del veneno perdió efecto en la invocación." + RESET, 15);

            }

            System.out.println("");

        }



    }
    private static void ContadorRecargaVaritasCombate() {
        ContRecargaBaston++;
        ContRecargaFuego++;
        ContRecargaHielo++;
        ContRecargaDebilidad++;
        ContRecargaCuracion++;
        ContRecargaProteccion++;

        if (ContadorConcentración > 0){
            ContRecargaBaston++;
            ContRecargaFuego++;
            ContRecargaHielo++;
            ContRecargaDebilidad++;
            ContRecargaCuracion++;
            ContRecargaProteccion++;
        }

        if (ContRecargaBaston >= 3) {
            ContRecargaBaston = 0;
            EnergiaVarita[0] += 1;
            if (EnergiaVarita[0] > MaxEnergiaVarita[0]) {
                EnergiaVarita[0] = MaxEnergiaVarita[0];
            }
        }
        if (ContRecargaFuego >= 7) {
            ContRecargaFuego = 0;
            EnergiaVarita[1] += 1;
            if (EnergiaVarita[1] > MaxEnergiaVarita[1]) {
                EnergiaVarita[1] = MaxEnergiaVarita[1];
            }
        }
        if (ContRecargaHielo >= 7) {
            ContRecargaHielo = 0;
            EnergiaVarita[2] += 1;
            if (EnergiaVarita[2] > MaxEnergiaVarita[2]) {
                EnergiaVarita[2] = MaxEnergiaVarita[2];
            }
        }
        if (ContRecargaDebilidad >= 7) {
            ContRecargaDebilidad = 0;
            EnergiaVarita[3] += 1;
            if (EnergiaVarita[3] > MaxEnergiaVarita[3]) {
                EnergiaVarita[3] = MaxEnergiaVarita[3];
            }
        }
        if (ContRecargaCuracion >= 7) {
            ContRecargaCuracion = 0;
            EnergiaVarita[4] += 1;
            if (EnergiaVarita[4] > MaxEnergiaVarita[4]) {
                EnergiaVarita[4] = MaxEnergiaVarita[4];
            }
        }
        if (ContRecargaProteccion >= 7) {
            ContRecargaProteccion = 0;
            EnergiaVarita[5] += 1;
            if (EnergiaVarita[5] > MaxEnergiaVarita[5]) {
                EnergiaVarita[5] = MaxEnergiaVarita[5];
            }
        }

    }
    private static void ContadorRecargaVaritasViajando() {
        ContRecargaBaston2++;
        ContRecargaFuego2++;
        ContRecargaHielo2++;
        ContRecargaDebilidad2++;
        ContRecargaCuracion2++;
        ContRecargaProteccion2++;

        if (ContadorConcentración > 0){
            ContRecargaBaston2++;
            ContRecargaFuego2++;
            ContRecargaHielo2++;
            ContRecargaDebilidad2++;
            ContRecargaCuracion2++;
            ContRecargaProteccion2++;

            ContadorConcentración--;
        }


        if (ContRecargaBaston2 >= 1) {
            ContRecargaBaston = 0;
            EnergiaVarita[0] += 2;
            if (EnergiaVarita[0] > MaxEnergiaVarita[0]) {
                EnergiaVarita[0] = MaxEnergiaVarita[0];
            }
        }
        if (ContRecargaFuego2 >= 2) {
            ContRecargaFuego = 0;
            EnergiaVarita[1] += 1;
            if (EnergiaVarita[1] > MaxEnergiaVarita[1]) {
                EnergiaVarita[1] = MaxEnergiaVarita[1];
            }
        }
        if (ContRecargaHielo2 >= 2) {
            ContRecargaHielo = 0;
            EnergiaVarita[2] += 1;
            if (EnergiaVarita[2] > MaxEnergiaVarita[2]) {
                EnergiaVarita[2] = MaxEnergiaVarita[2];
            }
        }
        if (ContRecargaDebilidad2 >= 2) {
            ContRecargaDebilidad = 0;
            EnergiaVarita[3] += 1;
            if (EnergiaVarita[3] > MaxEnergiaVarita[3]) {
                EnergiaVarita[3] = MaxEnergiaVarita[3];
            }
        }
        if (ContRecargaCuracion2 >= 2) {
            ContRecargaCuracion = 0;
            EnergiaVarita[4] += 1;
            if (EnergiaVarita[4] > MaxEnergiaVarita[4]) {
                EnergiaVarita[4] = MaxEnergiaVarita[4];
            }
        }
        if (ContRecargaProteccion2 >= 3) {
            ContRecargaFuego = 0;
            EnergiaVarita[5] += 1;
            if (EnergiaVarita[5] > MaxEnergiaVarita[5]) {
                EnergiaVarita[5] = MaxEnergiaVarita[5];
            }
        }

    }
    private static void ContadoresAlteradoresEstadosNegativos() {

        if (FuegoVarita > 0){
            SaludEnemigo -= FuegoVarita;
            imprimirPocoAPoco(VERDE + "  * Daño a enemigo por quemadura: " + FuegoVarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 15);
            imprimirPocoAPoco(BLANCO + "  Turnos de quemadura a enemigo restante: " + FuegoVarita + RESET, 15);

            FuegoVarita--;
            if (FuegoVarita <= 0){
                FuegoVarita = 0;
                imprimirPocoAPoco(BLANCO + "  La alteración de estado de tu varita de fuego perdio efecto." + RESET,15);
            }
            System.out.println("");
        }


        if (ContadorVeneno[0] > 0){
            SaludEnemigo -= ContadorVeneno[0];
            imprimirPocoAPoco(VERDE + "  * Daño a enemigo por veneno: " + ContadorVeneno[0] + ", Salud enemigo actual: " + SaludEnemigo + RESET, 15);
            imprimirPocoAPoco(BLANCO + "  Turnos de veneno a enemigo restante: " + ContadorVeneno[0] + RESET, 15);

            ContadorVeneno[0]--;
            if (ContadorVeneno[0] <= 0){
                ContadorVeneno[0] = 0;
                imprimirPocoAPoco(BLANCO + "  La alteración de estado del veneno perdio efecto." + RESET,15);

            }

            System.out.println("");

        }



    }


    private static void inicializarMatrizEnemigos() {
        InventarioEnemigo[0] = 0;//vida
        InventarioEnemigo[1] = 0;//agilidad
        InventarioEnemigo[2] = 0;//fuerza
        InventarioEnemigo[3] = 0;//resistencia
        InventarioEnemigo[4] = 0;//velocidad
        InventarioEnemigo[5] = 0;//tormenta
        InventarioEnemigo[6] = 0;//veneno
    }
    private static void asignarInventarioEnemigo() {
        Random random = new Random();

        // Reiniciar el inventario del enemigo
        Arrays.fill(InventarioEnemigo, 0);

        // Niveles de objetos según el nivel del enemigo
        switch (NivelEnemigo) {
            case "Nivel-1":
                InventarioEnemigo[0] = random.nextInt(1) + 0; // Vida
                InventarioEnemigo[1] = random.nextInt(3) + 0; // Agilidad
                InventarioEnemigo[2] = random.nextInt(1) + 0; // Fuerza
                break;
            case "Nivel-2":
                InventarioEnemigo[0] = random.nextInt(2) + 0; // Vida
                InventarioEnemigo[1] = random.nextInt(2) + 1; // Agilidad
                InventarioEnemigo[2] = random.nextInt(2) + 0; // Fuerza
                InventarioEnemigo[3] = random.nextInt(2) + 0; // Resistencia
                InventarioEnemigo[4] = random.nextInt(1) + 0; // Velocidad
                break;
            case "Nivel-3":
                InventarioEnemigo[0] = random.nextInt(3) + 1; // Vida
                InventarioEnemigo[1] = random.nextInt(3) + 0; // Agilidad
                InventarioEnemigo[2] = random.nextInt(3) + 0; // Fuerza
                InventarioEnemigo[3] = random.nextInt(2) + 1; // Resistencia
                InventarioEnemigo[4] = random.nextInt(3) + 0; // Velocidad
                InventarioEnemigo[5] = random.nextInt(2) + 0; // Tormenta
                InventarioEnemigo[6] = random.nextInt(2) + 0; // Veneno
                break;
            default:
                // Nivel desconocido, no se asignan objetos
                break;
        }
    }
    private static void DecicionEnemigo() {
        Random random = new Random();

        // Decidir opción en función del inventario del enemigo
        switch (NivelEnemigo) {
            case "Nivel-1":
                switch (random.nextInt(3)) {
                    case 0:
                        InventarioEnemigoVida();
                        break;
                    case 1:
                        InventarioEnemigoAgilidad();
                        break;
                    case 2:
                        InventarioEnemigoFuerza();
                        break;
                    default:
                        break;
                }
                break;
            case "Nivel-2":
                switch (random.nextInt(5)) {
                    case 0:
                        InventarioEnemigoVida();
                        break;
                    case 1:
                        InventarioEnemigoAgilidad();
                        break;
                    case 2:
                        InventarioEnemigoFuerza();
                        break;
                    case 3:
                        InventarioEnemigoResistencia();
                        break;
                    case 4:
                        InventarioEnemigoVelocidad();
                        break;
                    default:
                        break;
                }
                break;
            case "Nivel-3":
                switch (random.nextInt(7)) {
                    case 0:
                        InventarioEnemigoVida();
                        break;
                    case 1:
                        InventarioEnemigoAgilidad();
                        break;
                    case 2:
                        InventarioEnemigoFuerza();
                        break;
                    case 3:
                        InventarioEnemigoResistencia();
                        break;
                    case 4:
                        InventarioEnemigoVelocidad();
                        break;
                    case 5:
                        InventarioEnemigoFrascoTormenta();
                        break;
                    case 6:
                        InventarioEnemigoVeneno();
                        break;
                    default:
                        break;
                }
                break;
            default:
                // Nivel desconocido, no se hace nada
                break;
        }
    }

    private static void ContadoresEnemigo() {

        if (ContadorAgilidad[2] > 0) {
            ContadorAgilidad[2]--;
            if (ContadorAgilidad[2] <= 0) {
                imprimirPocoAPoco(BLANCO + "  La poción de agilidad del enemigo, dejó de hacer efecto." + RESET, 10);
                AgilidadEnemigoPocion = 0;
            }
        }
        if (ContadorFuerza[2] > 0) {
            ContadorFuerza[2]--;
            if (ContadorFuerza[2] <= 0) {
                imprimirPocoAPoco(BLANCO + "  La poción de Fuerza del enemigo, dejó de hacer efecto." + RESET, 10);
                DañoEnemigoPocion = 0;
            }
        }
        if (ContadorResistencia[2] > 0) {
            ContadorResistencia[2]--;
            if (ContadorResistencia[2] <= 0) {
                imprimirPocoAPoco(BLANCO + "  La poción de resistencia del enemigo, dejó de hacer efecto." + RESET, 10);
                ContadorResistencia[2] = 0;
            }
        }
        if (ContadorVelocidad[2] > 0) {
            ContadorVelocidad[2]--;
            if (ContadorVelocidad[2] <= 0) {
                imprimirPocoAPoco(BLANCO + "  La poción de velocidad  del enemigo,dejó de hacer efecto." + RESET, 10);
                ContadorVelocidad[2] = 0;
            }
        }

        if (ContadorVeneno[2] > 0){
            Salud[0] -= ContadorVeneno[2];
            imprimirPocoAPoco(VERDE + "  * Daño a jugador por veneno: " + ContadorVeneno[2] + ", Salud del jugador actual: " + Salud[0] + RESET, 20);
            imprimirPocoAPoco(BLANCO + "  Turnos de veneno a jugador restante: " + ContadorVeneno[2] + RESET, 20);

            ContadorVeneno[2]--;
            if (ContadorVeneno[2] <= 0){
                ContadorVeneno[2] = 0;
                imprimirPocoAPoco(BLANCO + "  La alteración de estado del veneno perdio efecto, en el jugador." + RESET,10);

            }
        }

        if (ContadorFuego[0] > 0){
            Salud[0] -= ContadorFuego[0];
            imprimirPocoAPoco(VERDE + "  * Daño a jugador por quemadura: " + ContadorFuego[0] + ", Salud del jugador actual: " + Salud[0] + RESET, 20);
            imprimirPocoAPoco(BLANCO + "  Turnos de quemadura a jugador restante: " + ContadorFuego[0] + RESET, 20);

            ContadorFuego[0]--;
            if (ContadorFuego[0] <= 0){
                ContadorFuego[0] = 0;
                imprimirPocoAPoco(BLANCO + "  La alteración de estado de la quemadura perdio efecto, en el jugador." + RESET,10);

            }
        }
        if (ContadorFuego[1] > 0){
            SaludInvocado -= ContadorFuego[1];
            imprimirPocoAPoco(VERDE + "  * Daño a invocación por quemadura: " + ContadorFuego[1] + ", Salud de invocación actual: " + SaludInvocado + RESET, 20);
            imprimirPocoAPoco(BLANCO + "  Turnos de quemadura a invocación restante: " + ContadorFuego[1] + RESET, 20);

            ContadorFuego[1]--;
            if (ContadorFuego[1] <= 0){
                ContadorFuego[1] = 0;
                imprimirPocoAPoco(BLANCO + "  La alteración de estado de la quemadura perdio efecto, en la invocación." + RESET,10);

            }
        }

    }

    private static void InventarioEnemigoVida() {
        Random random = new Random();
        if (InventarioEnemigo[0] > 0 && random.nextInt(11) <= 5 && SaludEnemigo <= 25) {
            InventarioEnemigo[0] -= 1;
            SaludEnemigo += 15;
            imprimirPocoAPoco(ROJO + "  "  + EnemigoActualNombre + " uso: poción de vida, +15 de salud." + RESET, 20);
            imprimirPocoAPoco(AMARILLO + "  * Salud de enemigo actual: " + SaludEnemigo + RESET, 20);
        }
    }
    private static void InventarioEnemigoAgilidad() {
        Random random = new Random();
        if (InventarioEnemigo[1] > 0 && random.nextInt(11) < 6 && ContadorTurnos > 3) {
            InventarioEnemigo[1] -= 1;
            ContadorAgilidad[2] = 4;
            AgilidadEnemigoPocion = 3;
            imprimirPocoAPoco(ROJO + "  "  + EnemigoActualNombre + " uso: poción de agilidad, +3 de agilidad." + RESET, 20);
            imprimirPocoAPoco(ROJO + "  * Agilidad del enemigo actual: " + (AgilidadEnemigo + AgilidadEnemigoPocion) + RESET, 20);

        }
    }
    private static void InventarioEnemigoFuerza() {
        Random random = new Random();
        if (InventarioEnemigo[2] > 0 && random.nextInt(11) < 6) {
            InventarioEnemigo[2] -= 1;
            ContadorFuerza[2] = 4;
            DañoEnemigoPocion = 3;
            imprimirPocoAPoco(ROJO + "  "  + EnemigoActualNombre + " uso: poción de fuerza, +3 de daño." + RESET, 20);
            imprimirPocoAPoco(ROJO + "  * Fuerza del enemigo actual: " + DañoMinEnemigo + " - " + + (DañoMaxEnemigo + DañoEnemigoPocion) + RESET, 20);
        }
    }
    private static void InventarioEnemigoResistencia() {
        Random random = new Random();
        if (InventarioEnemigo[3] > 0 && random.nextInt(11) < 6) {
            InventarioEnemigo[3] -= 1;
            ContadorResistencia[2] = 4;
            imprimirPocoAPoco(ROJO + "  "  + EnemigoActualNombre + " uso: poción de resistencia." + RESET, 20);
            imprimirPocoAPoco(ROJO + "  * Daño recibido: -20% "+ RESET, 20);
        }
    }
    private static void InventarioEnemigoVelocidad() {
        Random random = new Random();
        if (InventarioEnemigo[4] > 0 && random.nextInt(11) < 4) {
            InventarioEnemigo[4] -= 1;
            ContadorVelocidad[2] = 3;
            imprimirPocoAPoco(ROJO + "  "  + EnemigoActualNombre + " uso: poción de velocidad." + RESET, 20);
            imprimirPocoAPoco(ROJO + "  * Velocidad de ataque de enemigo: x2 "+ RESET, 20);
        }
    }
    private static void InventarioEnemigoFrascoTormenta() {
        Random random = new Random();
        if (InventarioEnemigo[5] > 0 && random.nextInt(11) < 7 && ContadorTurnos > 4) {
            InventarioEnemigo[5] -= 1;
            imprimirPocoAPoco(ROJO + "  "  + EnemigoActualNombre + " uso: frasco de tormenta." + RESET, 20);
            if (ContadorResistencia[0] > 0){

                if (Salud[4] > 0){
                    Salud[4] -= 40;
                    imprimirPocoAPoco(CYAN + "  -> El escudo mágico, recibe el daño: 40", 10);
                    if (Salud[4] < 0){Salud[4] = 0;
                        imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                    }
                    else {
                        imprimirPocoAPoco("  * Durabilidad del escudo mágico actual: " + Salud[4] + RESET, 15);
                    }

                }else{
                    Salud[0] -= 20;
                    imprimirPocoAPoco(VERDE + "  {**} Daño reducido, por poción de resistencia: -50%", 10);
                    imprimirPocoAPoco(ROJO + "  * Daño a jugador por hechizo de tormenta: 20" + RESET, 10);
                }

            }
            else {
                if (Salud[4] > 0){
                    Salud[4] -= 40;
                    imprimirPocoAPoco(CYAN + "  -> El escudo mágico, recibe el daño: 40", 10);
                    if (Salud[4] < 0){Salud[4] = 0;
                        imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                    }
                    else {
                        imprimirPocoAPoco("  * Durabilidad del escudo mágico actual: " + Salud[4] + RESET, 15);
                    }

                }else{
                    Salud[0] -= 40;
                    imprimirPocoAPoco(ROJO + "  * Daño a jugador por hechizo de tormenta: 40" + RESET, 10);
                }

            }

            imprimirPocoAPoco(ROJO + "  * Daño a jugador por hechizo de tormenta: 25" + RESET, 20);
            imprimirPocoAPoco(VERDE + "  * Salud del jugador actual: " + Salud[0] + RESET, 20);
            if (Salud[0] <= 0) {
                imprimirPocoAPoco(ROJO + "  ¡Has muerto, por frasco de tormenta!" + RESET, 20);
                imprimirPocoAPoco("  El programa se cerrara en breve", 2);
                imprimirPocoAPoco("  . . . . . . . . . . . . . .", 500);
                System.exit(0);
            }
            if (MonstruoEnCombate){
                int DañoColateralInvocacionRandom = (int) (Math.random() * 10) + 1;
                if (DañoColateralInvocacionRandom == 10 || DañoColateralInvocacionRandom == 1 || DañoColateralInvocacionRandom == 5) {
                    imprimirPocoAPoco(AMARILLO + "  La tormenta es liberada y se sale de control.", 20);
                    if (ContadorResistencia[1] > 0){
                        SaludInvocado -= 10;
                        imprimirPocoAPoco(VERDE + "  {**} Daño reducido, por poción de resistencia: -50%", 10);
                        imprimirPocoAPoco(AMARILLO + "  * Daño a " + NombreInvocado + " por hechizo de tormenta: 10" + RESET, 10);
                    }
                    else {
                        SaludInvocado -= 20;
                        imprimirPocoAPoco(AMARILLO + "  * Daño a " + NombreInvocado + " por hechizo de tormenta: 20" + RESET, 10);
                    }

                    imprimirPocoAPoco(VERDE + "  * Salud de la invocación actual: " + SaludInvocado + RESET, 20);
                    if (SaludInvocado<= 0) {
                        MonstruoEnCombate = false;
                        imprimirPocoAPoco(ROJO + "  ¡La invocación es derrotada por la tormenta y se retira del combate!" + RESET, 20);

                    }
                }

            }

            int DañoColateralRandom = (int) (Math.random() * 10) + 1;
            if (DañoColateralRandom == 10 || DañoColateralRandom == 1 || DañoColateralRandom == 5) {
                imprimirPocoAPoco(AMARILLO + "  La tormenta es liberada y se sale de control.", 20);
                if (ContadorResistencia[2] > 0){
                    SaludEnemigo -= 10;
                    imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: -50%", 10);
                    imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo por hechizo de tormenta: 10" + RESET, 10);
                }
                else {
                    SaludEnemigo -= 20;
                    imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo por hechizo de tormenta: 20" + RESET, 10);
                }

                imprimirPocoAPoco(VERDE + "  * Salud del enemigo actual: " + SaludEnemigo + RESET, 20);
            }
        }
    }
    private static void InventarioEnemigoVeneno(){
        Random random = new Random();
        if (InventarioEnemigo[6] > 0 && random.nextInt(11) < 6 && ContadorTurnos > 2) {
            InventarioEnemigo[6] -= 1;
            ContadorVeneno[2] = 5;
            imprimirPocoAPoco(ROJO + "  "  + EnemigoActualNombre + " uso: frasco de veneno." + RESET, 20);
            imprimirPocoAPoco(ROJO + "  * Valor de veneno a jugador: 5"+ RESET, 20);
        }
    }

    private static void VerificarEnemigoHabilidad(){
        // Lista de índices especiales


        List<Integer> IndicesEspeciales = Arrays.asList(9, 11);

        // Mapa de combinaciones (nivel, índice) -> acción y nombre de habilidad
        Map<String, Runnable> acciones = new HashMap<>();
        Map<String, String> habilidades = new HashMap<>();  // Mapa para almacenar el nombre de la habilidad

        acciones.put("1-9", () -> HabilidadFuego());
        acciones.put("1-11", () -> HabilidadFuego());

        // Nombres de habilidades asociadas
        habilidades.put("1-9", "Quemadura.");
        habilidades.put("1-11", "Quemadura.");




        // Verificamos si el índice está en la lista de especiales
        if (IndicesEspeciales.contains(IndiceEnemigo)) {
            // Creamos la clave para buscar en el mapa
            String clave = EnemigoNivelSelec + "-" + IndiceEnemigo;
            Runnable accion = acciones.get(clave);
            String habilidad = habilidades.get(clave);

            if (accion != null) {

                if (ContadorTurnos > 1) {
                    accion.run();  // Ejecuta la acción asociada
                }
                if (ContadorTurnos <= 1) {
                    HabilidadActualEnemigo = habilidad;  // Almacenar el nombre de la habilidad
                }
            }

        }
    }
    private static void HabilidadFuego(){
        Random random = new Random();
        if (MonstruoEnCombate){
            ContadorFuego[1]=2;
            imprimirPocoAPoco(ROJO + "  "  + EnemigoActualNombre + " le aplico quemadura a la invocación." + RESET, 20);
            imprimirPocoAPoco(ROJO + "  * Valor de quemadura a invocación: 2"+ RESET, 20);
        }
        else if (random.nextInt(11) < 5) {
            ContadorFuego[0]=2;
            imprimirPocoAPoco(ROJO + "  "  + EnemigoActualNombre + " te aplico quemadura." + RESET, 20);
            imprimirPocoAPoco(ROJO + "  * Valor de quemadura a jugador: 2"+ RESET, 20);
        }


    }

    private static void AgregarNombre2() {
        boolean nombreValido = false;
        Scanner scanner = new Scanner(System.in);

        List<String> palabrasProhibidas = Arrays.asList(
                "puto", "pito", "semen", "putoelquelea", "puoelquelea", "cago", "caga",
                "benito", "camelo", "pene", "mierda", "sexo", "culo", "culito", "melo", "chupa", "perra",
                "roberto", "tragatelo", "coño", "verga", "puta", "maricón", "maricon", "fuck", "masturbar",
                "shit", "asshole", "bitch", "cunt", "dick", "cock", "pussy", "bastard", "motherfucker", "ggg", "qqq", "sabrosona", "sabrosa",
                "culero", "culera", "maldita", "mamada", "gay", "gy", "tonto", "bastardo", "coger", "idiota",
                "maldito", "maldita", "estupido", "estupida", "polla", "tocamelo", "ticamela", "verga", "xxx", "xxz", "leche", "porno", "sexo",
                "gey", "droga", "marigu", "madre", "pendejo", "pendeja", "pta","pto", "put", "gai", "pitudo", "sexi", "sexy", "pela", "sexy", "jalame",
                "coge", "prr", "vergo", "mama", "pit0", "dr0ga", "put0", "6ei", "6e1", "sex0", "6ai", "6ay","64y", "porn0", "p0rn0", "pndejo", "pndeja", "pn",
                "i?","ene", "pndejo", "pndj", "pn", "pt"
        );

        while (!nombreValido) {
            System.out.println("");
            imprimirPocoAPoco("  Por favor, nombra a tu enemigo.", 20);
            String nombreIngresado = scanner.nextLine();
            String nombrecopia = nombreIngresado;
            // Normaliza el nombre
            String nombreNormalizado = nombreIngresado.toLowerCase()
                    .replaceAll("0", "o")
                    .replaceAll("1", "i")
                    .replaceAll("2", "r")
                    .replaceAll("3", "e")
                    .replaceAll("4", "a")
                    .replaceAll("5", "s")
                    .replaceAll("6", "g")
                    .replaceAll("7", "t")
                    .replaceAll("8", "b")
                    .replaceAll("9", "q")
                    .replaceAll("!", "i")
                    .replaceAll("¡", "i")
                    .replaceAll("¿", "d")
                    .replaceAll("ñ", "n")
                    .replaceAll("Ñ", "N")
                    .replaceAll("á", "a")
                    .replaceAll("é", "e")
                    .replaceAll("í", "i")
                    .replaceAll("ó", "o")
                    .replaceAll("ú", "u")
                    .replaceAll("Á", "a")
                    .replaceAll("É", "e")
                    .replaceAll("Í", "i")
                    .replaceAll("Ó", "o")
                    .replaceAll("Ú", "u")
                    .replaceAll("\\|", "i") // Reemplaza "|" con "i"
                    .replaceAll("[^a-z0-9]", "") // Elimina caracteres especiales y espacios
                    .replaceAll("(.)\\1+", "$1"); // Elimina caracteres repetidos consecutivos
            System.out.println("  Validando: " + nombreNormalizado);
            imprimirPocoAPoco(" . . .", 400);
            // Normaliza el nombre


            boolean contienePalabraProhibida = false;
            for (String palabra : palabrasProhibidas) {
                if (nombreNormalizado.contains(palabra)) {
                    contienePalabraProhibida = true;
                    break;
                }
            }

            if (contienePalabraProhibida) {
                imprimirPocoAPoco(ROJO + "  El enemigo se niega a portar ese nombre.    (*∩*)" + RESET, 10);

                System.out.println(AZUL + "  Presiona Enter para continuar." + RESET);
                scanner.nextLine();
                borrarConsola(10);
            } else {
                nombreValido = true;
                // NombreJugador = nombreIngresado; // Guarda el nombre original
                // Normaliza el nombre
                EnemigoActualNombre = nombrecopia
                        .replaceAll("[^a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ]", "");
                System.out.println("");
                imprimirPocoAPoco(VERDE + "  Nombre aceptado: " + EnemigoActualNombre + RESET, 20);
            }
        }
    }
    private static void EnemigoEspecificoPerso() {
        EnemigoMagico = false;
        boolean inputValido;
        int nivelEnemigo = 0;
        Scanner scanner = new Scanner(System.in);
        imprimirPocoAPoco( CYAN + "  Enemigo especial, personaliza tu enemigo. " + RESET, 10);
        AgregarNombre2();

        do {
            imprimirPocoAPoco( VERDE + "  Ingrese el nivel del enemigo (1 al 3): " + RESET, 10);
            try {
                nivelEnemigo = scanner.nextInt();
                inputValido = true;
                if (nivelEnemigo <= 1){
                    NivelEnemigo = "Nivel 1";
                }
                else if (nivelEnemigo == 2){
                    NivelEnemigo = "Nivel 2";
                }
                else{
                    NivelEnemigo = "Nivel 3";
                }
            } catch (InputMismatchException e) {
                imprimirPocoAPoco(ROJO + "  Por favor, ingrese un número válido."  + RESET, 10);
                inputValido = false;
                scanner.next(); // Limpiar el buffer del scanner
            }
        } while (!inputValido);

        do {
            imprimirPocoAPoco(  VERDE + "  Ingrese la salud del enemigo (Min: 25): " + RESET, 10);
            try {
                SaludEnemigo = scanner.nextInt();
                inputValido = true;
                if (SaludEnemigo <= 25){
                    SaludEnemigo = 25;
                }
                if (SaludEnemigo >= 1000)
                {
                    SaludEnemigo = 1000;
                }
            } catch (InputMismatchException e) {
                imprimirPocoAPoco(ROJO + "  Por favor, ingrese un número válido." + RESET, 10);
                inputValido = false;
                scanner.next(); // Limpiar el buffer del scanner
            }
        } while (!inputValido);

        do {
            imprimirPocoAPoco( VERDE + "  Ingrese el daño mínimo del enemigo (Min: 1, Máx: 25): " + RESET, 10);
            try {
                DañoMinEnemigo = scanner.nextInt();
                inputValido = true;
                if (DañoMinEnemigo <= 1){
                    DañoMinEnemigo = 1;
                }
                if (DañoMinEnemigo >= 25){
                    DañoMinEnemigo = 25;
                }
            } catch (InputMismatchException e) {
                imprimirPocoAPoco(ROJO + "  Por favor, ingrese un número válido." + RESET, 10);
                inputValido = false;
                scanner.next();
            }
        } while (!inputValido);

        do {
            imprimirPocoAPoco( VERDE + "  Ingrese el daño máximo del enemigo: (Min: " + (DañoMinEnemigo + 1) +", Máx: 45)" + RESET, 10);
            try {
                DañoMaxEnemigo = scanner.nextInt();
                if (DañoMinEnemigo >= DañoMaxEnemigo){
                    DañoMaxEnemigo = DañoMinEnemigo + 5;
                }
                if (DañoMaxEnemigo >= 45){
                    DañoMaxEnemigo = 45;
                }
                inputValido = true;
            } catch (InputMismatchException e) {
                imprimirPocoAPoco(ROJO + "  Por favor, ingrese un número válido." + RESET, 10);
                inputValido = false;
                scanner.next();
            }
        } while (!inputValido);

        do {
            imprimirPocoAPoco( VERDE + "  Ingrese la agilidad del enemigo (1 al 9): " + RESET, 10);
            try {
                int agilidadEnemigo = scanner.nextInt();
                inputValido = true;

                if (agilidadEnemigo <= 1){
                    AgilidadEnemigo = 1;
                }
                else if (agilidadEnemigo >= 9){
                    AgilidadEnemigo = 9;
                }
                else {
                    AgilidadEnemigo = agilidadEnemigo;
                }

            } catch (InputMismatchException e) {
                imprimirPocoAPoco(ROJO + "  Por favor, ingrese un número válido." + RESET, 10);
                inputValido = false;
                scanner.next();
            }
        } while (!inputValido);

        do {
            imprimirPocoAPoco( VERDE + "  Ingrese la probabilidad de crítico del enemigo (entre 1 y 99): " + RESET, 10);
            try {
                int probabilidadCriticoInt = scanner.nextInt();

                if (probabilidadCriticoInt <= 1){
                    probabilidadCriticoInt = 1;
                }
                else if (probabilidadCriticoInt >= 99){
                    probabilidadCriticoInt = 99;
                }
                ProbabilidadCriticoEnemigo = probabilidadCriticoInt / 100.0;
            } catch (InputMismatchException e) {
                imprimirPocoAPoco(ROJO + "  Por favor, ingrese un número válido." + RESET, 10);
                inputValido = false;
                scanner.next();
            }
        } while (!inputValido);

        System.out.println(CYAN + "  Cargando..."  + RESET);
        asignarInventarioEnemigo();
        imprimirPocoAPoco(" . . .", 300);
        borrarConsola(100);
        System.out.println("");

        if (nivelEnemigo <= 1){
            mostrarimagen("enemigoentidadmenor.txt");
        }
        else if (nivelEnemigo == 2){
            mostrarimagen("enemigoentidadnormal.txt");
        }
        else{
            mostrarimagen("enemigoentidadmayor.txt");
        }

        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de " + NivelEnemigo, 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  * Inventario:"  + AMARILLO , 15);
        if ( InventarioEnemigo[0] > 0){imprimirPocoAPoco(AMARILLO + "  * Poción de vida: " +  InventarioEnemigo[0], 5);}
        if ( InventarioEnemigo[1] > 0){imprimirPocoAPoco("  * Poción de agilidad: " +  InventarioEnemigo[1], 5);}
        if ( InventarioEnemigo[2] > 0){imprimirPocoAPoco("  * Poción de fuerza: " +  InventarioEnemigo[2], 5);}
        if ( InventarioEnemigo[3] > 0){imprimirPocoAPoco("  * Poción de resistencia: " +  InventarioEnemigo[3], 5);}
        if ( InventarioEnemigo[4] > 0){imprimirPocoAPoco("  * Poción de velocidad: " +  InventarioEnemigo[4], 5);}
        if ( InventarioEnemigo[5] > 0){imprimirPocoAPoco("  * Frasco de tormenta: " +  InventarioEnemigo[5], 5);}
        if ( InventarioEnemigo[6] > 0){imprimirPocoAPoco("  * Frasco de veneno: " +  InventarioEnemigo[6], 5);}
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        scanner.nextLine();

    }

    public enum TipoSeleccion {
        ALEATORIO_BOSQUE,
        ALEATORIO_LAVA,
        ALEATORIO_CASTILLO,
        ALEATORIO_GEMA,
        ALEATORIO_ELFO,
        ESPECIFICO,
        ALEATORIO_TOTAL
    }

    public enum GrupoE {
        BOSQUE,
        LAVA,
        CASTILLO,
        GEMA,
        ELFO,
        ESPECIALES
    }

    //AsignarAtributosEnemigo(grupoE, EnemigoNivelSelec, EnemigoIndiceSelec, EnemigoCategoriaSelec);
    public static void AsignarAtributosEnemigo(GrupoE grupoE, int nivel, int indice, TipoSeleccion tipoSeleccion) {
        Random random = new Random();
        int columnas = 0;
        // Determinar el índice de enemigo basado en el tipo de selección
        switch (tipoSeleccion) {
            case ESPECIFICO:
                // Mantener el índice proporcionado
                break;
            case ALEATORIO_BOSQUE:
                if (nivel > 0 && nivel <= EBosque.length) {
                    columnas = EBosque[nivel - 1].length;
                    indice = random.nextInt(columnas);
                } else {
                    System.out.println("Nivel inválido en Bosque.");
                }
                break;

            case ALEATORIO_CASTILLO:
                if (nivel > 0 && nivel <= ECastillo.length) {
                    columnas = ECastillo[nivel - 1].length;
                    indice = random.nextInt(columnas);
                } else {
                    System.out.println("Nivel inválido en Castillo.");
                }
                break;

            case ALEATORIO_LAVA:
                if (nivel > 0 && nivel <= EMagma.length) {
                    columnas = EMagma[nivel - 1].length;
                    indice = random.nextInt(columnas);

                } else {
                    System.out.println("Nivel inválido en Lava.");
                }
                break;
            case ALEATORIO_GEMA:
                if (nivel > 0 && nivel <= EGemas.length) {
                    columnas = EGemas[nivel - 1].length;
                    indice = random.nextInt(columnas);

                } else {
                    System.out.println("Nivel inválido en Gema.");
                }
                break;

            case ALEATORIO_ELFO:
                if (nivel > 0 && nivel <= EElfos.length) {
                    columnas = EElfos[nivel - 1].length;
                    indice = random.nextInt(columnas);

                } else {
                    System.out.println("Nivel inválido en Elfo.");
                }
                break;

            case ALEATORIO_TOTAL:

                break;
            default:
                System.out.println("Tipo de selección no válido.");
                return;
        }




        // Asignar atributos comunes
        NivelEnemigo = "Nivel " + nivel;

        // Reiniciar el inventario del enemigo
        Arrays.fill(InventarioEnemigo, 0);
        if (nivel != 0) {
            asignarInventarioEnemigo();
        }

        IndiceEnemigo = indice;
        ImgEnemigo = ImgEnemigoN1[0];

        if(grupoE == GrupoE.BOSQUE){

            EnemigoActualNombre = EBosque[nivel - 1][indice];
            SaludEnemigo = (int) (Math.random() * ExtraSaludEB[nivel - 1][indice]) + MinSaludEB[nivel - 1][indice];
            DañoMinEnemigo = (int) (Math.random() * ExtraDañoEB[nivel - 1][indice]) + MinDañoEB[nivel - 1][indice];
            DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEB[nivel - 1][indice];
            AgilidadEnemigo = (int)  (Math.random() * AgilidadEB[nivel - 1][indice]) + 1;
            ProbabilidadCriticoEnemigo = PCriticoEB[nivel - 1][indice];

            // Verificar si es enemigo mágico
            EnemigoMagico = PMagicoEB[nivel - 1][indice];
            if (EnemigoMagico) {
                EnemigoMagico = Math.random() < 0.7;
                if (EnemigoMagico) {
                    DañoMagico = DañoMagicoEB[nivel - 1][indice];
                }
                else{
                    DañoMagico = 0;
                }
            }
        }
        else if(grupoE == GrupoE.LAVA){
            EnemigoActualNombre = EMagma[nivel - 1][indice];
            SaludEnemigo = (int) (Math.random() * ExtraSaludEM[nivel - 1][indice]) + MinSaludEM[nivel - 1][indice];
            DañoMinEnemigo = (int) (Math.random() * ExtraDañoEM[nivel - 1][indice]) + MinDañoEM[nivel - 1][indice];
            DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEM[nivel - 1][indice];
            AgilidadEnemigo = (int)  (Math.random() * AgilidadEM[nivel - 1][indice]) + 1;
            ProbabilidadCriticoEnemigo = PCriticoEM[nivel - 1][indice];

            // Verificar si es enemigo mágico
            EnemigoMagico = PMagicoEM[nivel - 1][indice];
            if (EnemigoMagico) {
                EnemigoMagico = Math.random() < 0.7;
                if (EnemigoMagico) {
                    DañoMagico = DañoMagicoEM[nivel - 1][indice];
                }
                else{
                    DañoMagico = 0;
                }
            }
        }
        else if(grupoE == GrupoE.GEMA){
            EnemigoActualNombre = EGemas[nivel - 1][indice];
            SaludEnemigo = (int) (Math.random() * ExtraSaludEG[nivel - 1][indice]) + MinSaludEG[nivel - 1][indice];
            DañoMinEnemigo = (int) (Math.random() * ExtraDañoEG[nivel - 1][indice]) + MinDañoEG[nivel - 1][indice];
            DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEG[nivel - 1][indice];
            AgilidadEnemigo = (int)  (Math.random() * AgilidadEG[nivel - 1][indice]) + 1;
            ProbabilidadCriticoEnemigo = PCriticoEG[nivel - 1][indice];

            // Verificar si es enemigo mágico
            EnemigoMagico = PMagicoEG[nivel - 1][indice];
            if (EnemigoMagico) {
                EnemigoMagico = Math.random() < 0.7;
                if (EnemigoMagico) {
                    DañoMagico = DañoMagicoEG[nivel - 1][indice];
                }
                else{
                    DañoMagico = 0;
                }
            }
        }
        else if(grupoE == GrupoE.ELFO){
            EnemigoActualNombre = EElfos[nivel - 1][indice];
            SaludEnemigo = (int) (Math.random() * ExtraSaludEEl[nivel - 1][indice]) + MinSaludEEl[nivel - 1][indice];
            DañoMinEnemigo = (int) (Math.random() * ExtraDañoEEl[nivel - 1][indice]) + MinDañoEEl[nivel - 1][indice];
            DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEEl[nivel - 1][indice];
            AgilidadEnemigo = (int)  (Math.random() * AgilidadEEl[nivel - 1][indice]) + 1;
            ProbabilidadCriticoEnemigo = PCriticoEEl[nivel - 1][indice];

            // Verificar si es enemigo mágico
            EnemigoMagico = PMagicoEEl[nivel - 1][indice];
            if (EnemigoMagico) {
                EnemigoMagico = Math.random() < 0.7;
                if (EnemigoMagico) {
                    DañoMagico = DañoMagicoEEl[nivel - 1][indice];
                }
                else{
                    DañoMagico = 0;
                }
            }
        }
        else if(grupoE == GrupoE.CASTILLO){
            EnemigoActualNombre = ECastillo[nivel - 1][indice];
            SaludEnemigo = (int) (Math.random() * ExtraSaludEC[nivel - 1][indice]) + MinSaludEC[nivel - 1][indice];
            DañoMinEnemigo = (int) (Math.random() * ExtraDañoEC[nivel - 1][indice]) + MinDañoEC[nivel - 1][indice];
            DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEC[nivel - 1][indice];
            AgilidadEnemigo = (int)  (Math.random() * AgilidadEC[nivel - 1][indice]) + 1;
            ProbabilidadCriticoEnemigo = PCriticoEC[nivel - 1][indice];

            // Verificar si es enemigo mágico
            EnemigoMagico = PMagicoEC[nivel - 1][indice];
            if (EnemigoMagico) {
                EnemigoMagico = Math.random() < 0.7;
                if (EnemigoMagico) {
                    DañoMagico = DañoMagicoEC[nivel - 1][indice];
                }
                else{
                    DañoMagico = 0;
                }
            }
        }
        else if(grupoE == GrupoE.ESPECIALES){
            EnemigoActualNombre = EEspeciales[nivel - 1][indice];
            SaludEnemigo = (int) (Math.random() * ExtraSaludEEs[nivel - 1][indice]) + MinSaludEEs[nivel - 1][indice];
            DañoMinEnemigo = (int) (Math.random() * ExtraDañoEEs[nivel - 1][indice]) + MinDañoEEs[nivel - 1][indice];
            DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEEs[nivel - 1][indice];
            AgilidadEnemigo = (int)  (Math.random() * AgilidadEEs[nivel - 1][indice]) + 1;
            ProbabilidadCriticoEnemigo = PCriticoEEs[nivel - 1][indice];

            // Verificar si es enemigo mágico
            EnemigoMagico = PMagicoEEs[nivel - 1][indice];
            if (EnemigoMagico) {
                EnemigoMagico = Math.random() < 0.7;
                if (EnemigoMagico) {
                    DañoMagico = DañoMagicoEEs[nivel - 1][indice];
                }
                else{
                    DañoMagico = 0;
                }
            }
        }
        else{
            imprimirPocoAPoco("  Error en la selección de grupoE.", 10);
        }

        // Mostrar atributos e inventario
        MostrarAtributosEnemigo(nivel, indice);
        MostrarInventarioEnemigo();
    }
    public static String ImgEnemigo = "";
    private static void MostrarAtributosEnemigo(int nivel, int indice) {
        System.out.println("");
        VerificarEnemigoHabilidad();
        mostrarimagen(ImgEnemigo);  // Muestra la imagen correspondiente al enemigo
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel " + nivel + ".", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        if (EnemigoMagico) {
            imprimirPocoAPoco("  * Daño Mágico: " + DañoMagico, 5);
        }
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        imprimirPocoAPoco("  * Habilidad: " + HabilidadActualEnemigo + RESET, 5);
        System.out.println("");
    }
    private static void MostrarInventarioEnemigo() {
        imprimirPocoAPoco(ROJO + "  * Inventario:" + AMARILLO, 15);
        if (InventarioEnemigo[0] > 0) imprimirPocoAPoco("  * Poción de vida: " + InventarioEnemigo[0], 5);
        if (InventarioEnemigo[1] > 0) imprimirPocoAPoco("  * Poción de agilidad: " + InventarioEnemigo[1], 5);
        if (InventarioEnemigo[2] > 0) imprimirPocoAPoco("  * Poción de fuerza: " + InventarioEnemigo[2], 5);
        if (InventarioEnemigo[3] > 0) imprimirPocoAPoco("  * Poción de resistencia: " + InventarioEnemigo[3], 5);
        if (InventarioEnemigo[4] > 0) imprimirPocoAPoco("  * Poción de velocidad: " + InventarioEnemigo[4], 5);
        if (InventarioEnemigo[5] > 0) imprimirPocoAPoco("  * Frasco de tormenta: " + InventarioEnemigo[5], 5);
        if (InventarioEnemigo[6] > 0) imprimirPocoAPoco("  * Frasco de veneno: " + InventarioEnemigo[6], 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        new Scanner(System.in).nextLine();
    }




    public static void AsignarAtributosEnemigoN1(int indice, TipoSeleccion tipoSeleccion) {
        Random random = new Random();
        switch (tipoSeleccion) {
            case ALEATORIO_BOSQUE:
                indice = random.nextInt(4);  // Aleatorio entre 0 y 3
                break;
            case ALEATORIO_LAVA:
                indice = random.nextInt(4) + 9;  // Aleatorio entre 9 y 12
                break;
            case ALEATORIO_TOTAL:
                indice = random.nextInt(14);
               /* int rangototal = random.nextInt(3)+1;
                switch (rangototal) {
                    case 1:
                        indice = random.nextInt(4);
                        break;
                    case 2:
                        indice = random.nextInt(1) + 6;
                        break;
                    case 3:
                        indice = random.nextInt(4);
                        break;
                    default:
                        System.out.println("Error de selección.");
                        return;
                }*/
                break;
            case ESPECIFICO:
                // No hace nada, usa el índice proporcionado
                break;
            default:
                System.out.println("Tipo de selección no válido.");
                return;
        }

        if (indice < 0 || indice >= NombreEnemigoN1.length || NombreEnemigoN1[indice].isEmpty()) {
            System.out.println("Enemigo no válido en el índice especificado.");
            return;
        }
        // Reiniciar el inventario del enemigo
        Arrays.fill(InventarioEnemigo, 0);
        NivelEnemigo = "Nivel 1"; EnemigoMagico = false;
        if (indice == 6 || indice == 8 || indice == 12) {
            asignarInventarioEnemigo();
        }
        IndiceEnemigo = indice; //------------------------------------

        EnemigoMagico = false;

        EnemigoActualNombre = NombreEnemigoN1[indice];
        SaludEnemigo = (int) (Math.random() * ExtraSaludEnemigoN1[indice]) + MinSaludEnemigoN1[indice];
        DañoMinEnemigo = (int) (Math.random() * ExtraDañoEnemigoN1[indice]) + MinDañoEnemigoN1[indice];
        DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEnemigoN1[indice];
        AgilidadEnemigo = (int)  (Math.random() * AgilidadEnemigoN1[indice]) + 1;
        ProbabilidadCriticoEnemigo = PCriticoEnemigoN1[indice];

        MostrarAtributosEnemigoN1(indice);
        MostrarInventarioEnemigo();
    }
    private static void MostrarAtributosEnemigoN1(int indice) {
        System.out.println("");
        VerificarEnemigoHabilidad();
        mostrarimagen(ImgEnemigoN1[indice]);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 1.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo, 5);
        imprimirPocoAPoco("  * Habilidad: " + HabilidadActualEnemigo + RESET, 5);
        System.out.println("");
    }

    public static void AsignarAtributosEnemigoN2(int indice, TipoSeleccion tipoSeleccion) {
        Random random = new Random();
        switch (tipoSeleccion) {
            case ALEATORIO_BOSQUE:
                indice = random.nextInt(6);  // Aleatorio entre 0 y 5
                break;
            case ALEATORIO_LAVA:
                indice = random.nextInt(6) + 12;  // Aleatorio entre 12 y 17
                break;
            case ESPECIFICO:
                // No hace nada, usa el índice proporcionado
                break;
            default:
                System.out.println("Tipo de selección no válido.");
                return;
        }
        if (indice < 0 || indice >= NombreEnemigoN2.length || NombreEnemigoN2[indice].isEmpty()) {
            System.out.println("Enemigo no válido en el índice especificado.");
            return;
        }
        IndiceEnemigo = indice; //------------------------------------
        // Reiniciar el inventario del enemigo
        Arrays.fill(InventarioEnemigo, 0);
        NivelEnemigo = "Nivel 2"; EnemigoMagico = false;
        if (indice == 0 || indice == 0 || indice == 0) {
            asignarInventarioEnemigo();
        }
        EnemigoMagico = PMagicoEnemigoN2[indice];
if (EnemigoMagico){
    EnemigoMagico = Math.random() < 0.7;
}
        EnemigoActualNombre = NombreEnemigoN2[indice];
        SaludEnemigo = (int) (Math.random() * ExtraSaludEnemigoN2[indice]) + MinSaludEnemigoN2[indice];
        DañoMinEnemigo = (int) (Math.random() * ExtraDañoEnemigoN2[indice]) + MinDañoEnemigoN2[indice];
        DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEnemigoN2[indice];
        AgilidadEnemigo = (int)  (Math.random() * AgilidadEnemigoN2[indice]) + 1;
        ProbabilidadCriticoEnemigo = PCriticoEnemigoN2[indice];
        DañoMagico = DañoMagicoEnemigoN2[indice];

        MostrarAtributosEnemigoN2(indice);
        MostrarInventarioEnemigo();
    }
    private static void MostrarAtributosEnemigoN2(int indice) {
        System.out.println("");
        VerificarEnemigoHabilidad();
        mostrarimagen(ImgEnemigoN2[indice]);  // Muestra la imagen correspondiente al enemigo
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 2.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        if (EnemigoMagico) {
            imprimirPocoAPoco("  * Daño Mágico: " + DañoMagico, 5);
        }
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        imprimirPocoAPoco("  * Habilidad: " + HabilidadActualEnemigo + RESET, 5);
        System.out.println("");
    }

    public static void AsignarAtributosEnemigoN3(int indice, TipoSeleccion tipoSeleccion) {
        Random random = new Random();
        switch (tipoSeleccion) {
            case ALEATORIO_BOSQUE:
                indice = random.nextInt(6);  // Aleatorio entre 0 y 5
                break;
            case ALEATORIO_LAVA:
                indice = random.nextInt(6) + 12;  // Aleatorio entre 12 y 17
                break;
            case ESPECIFICO:
                // No hace nada, usa el índice proporcionado
                break;
            default:
                System.out.println("Tipo de selección no válido.");
                return;
        }
        if (indice < 0 || indice >= NombreEnemigoN3.length || NombreEnemigoN3[indice].isEmpty()) {
            System.out.println("Enemigo no válido en el índice especificado.");
            return;
        }
        IndiceEnemigo = indice; //------------------------------------

        // Reiniciar el inventario del enemigo
        Arrays.fill(InventarioEnemigo, 0);
        NivelEnemigo = "Nivel 3"; EnemigoMagico = false;
        if (indice == 0 || indice == 0 || indice == 0) {
            asignarInventarioEnemigo();
        }
        EnemigoMagico = PMagicoEnemigoN3[indice];
        if (EnemigoMagico){
            EnemigoMagico = Math.random() < 0.5;
        }
        EnemigoActualNombre = NombreEnemigoN3[indice];
        SaludEnemigo = (int) (Math.random() * ExtraSaludEnemigoN3[indice]) + MinSaludEnemigoN3[indice];
        DañoMinEnemigo = (int) (Math.random() * ExtraDañoEnemigoN3[indice]) + MinDañoEnemigoN3[indice];
        DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEnemigoN3[indice];
        AgilidadEnemigo = (int)  (Math.random() * AgilidadEnemigoN3[indice]) + 1;
        ProbabilidadCriticoEnemigo = PCriticoEnemigoN3[indice];
        DañoMagico = DañoMagicoEnemigoN3[indice];

        MostrarAtributosEnemigoN3(indice);
        MostrarInventarioEnemigo();
    }
    private static void MostrarAtributosEnemigoN3(int indice) {
        System.out.println("");
        VerificarEnemigoHabilidad();
        mostrarimagen(ImgEnemigoN3[indice]);  // Muestra la imagen correspondiente al enemigo
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 3.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        if (EnemigoMagico) {
            imprimirPocoAPoco("  * Daño Mágico: " + DañoMagico, 5);
        }
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        imprimirPocoAPoco("  * Habilidad: " + HabilidadActualEnemigo + RESET, 5);
        System.out.println("");
    }

    public static void AsignarAtributosEnemigoN4(int indice, TipoSeleccion tipoSeleccion) {
        Random random = new Random();
        switch (tipoSeleccion) {
            case ALEATORIO_BOSQUE:
                indice = random.nextInt(6);  // Aleatorio entre 0 y 5
                break;
            case ALEATORIO_LAVA:
                indice = random.nextInt(6) + 12;  // Aleatorio entre 12 y 17
                break;
            case ESPECIFICO:
                // No hace nada, usa el índice proporcionado
                break;
            default:
                System.out.println("Tipo de selección no válido.");
                return;
        }
        if (indice < 0 || indice >= NombreEnemigoN4.length || NombreEnemigoN4[indice].isEmpty()) {
            System.out.println("Enemigo no válido en el índice especificado.");
            return;
        }
        IndiceEnemigo = indice; //------------------------------------

        // Reiniciar el inventario del enemigo
        Arrays.fill(InventarioEnemigo, 0);
        NivelEnemigo = "Nivel 4"; EnemigoMagico = false;
        if (indice == 0 || indice == 0 || indice == 0) {
            asignarInventarioEnemigo();
        }
        EnemigoMagico = PMagicoEnemigoN4[indice];
        if (EnemigoMagico){
            EnemigoMagico = Math.random() < 0.2;
        }
        EnemigoActualNombre = NombreEnemigoN4[indice];
        SaludEnemigo = (int) (Math.random() * ExtraSaludEnemigoN4[indice]) + MinSaludEnemigoN4[indice];
        DañoMinEnemigo = (int) (Math.random() * ExtraDañoEnemigoN4[indice]) + MinDañoEnemigoN4[indice];
        DañoMaxEnemigo = DañoMinEnemigo + MaxDañoEnemigoN4[indice];
        AgilidadEnemigo = AgilidadEnemigoN4[indice];
        ProbabilidadCriticoEnemigo = PCriticoEnemigoN4[indice];
        DañoMagico = DañoMagicoEnemigoN4[indice];

        MostrarAtributosEnemigoN4(indice);
        MostrarInventarioEnemigo();
    }
    private static void MostrarAtributosEnemigoN4(int indice) {
        System.out.println("");
        VerificarEnemigoHabilidad();
        mostrarimagen(ImgEnemigoN4[indice]);  // Muestra la imagen correspondiente al enemigo
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 4.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        if (EnemigoMagico) {
            imprimirPocoAPoco("  * Daño Mágico: " + DañoMagico, 5);
        }
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        imprimirPocoAPoco("  * Habilidad: " + HabilidadActualEnemigo + RESET, 5);
        System.out.println("");
    }


    private static void EnemigoEspecificoDuende() {
        asignarInventarioEnemigo();
        EnemigoMagico = false;

        EnemigoActualNombre = "";
        Scanner scanner = new Scanner(System.in);
        SaludEnemigo = (int) (Math.random() * 25) + 15;
        DañoMinEnemigo = (int)  (Math.random() * 6) + 3;
        DañoMaxEnemigo = DañoMinEnemigo + 5;
        AgilidadEnemigo = (int) (Math.random() * 5) + 1;
        ProbabilidadCriticoEnemigo = 0.34;
        System.out.println("");
        mostrarimagen("enemigoduendeelite.txt");
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 1.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo))+ "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  * Inventario:" + AMARILLO , 15);
        if ( InventarioEnemigo[0] > 0){imprimirPocoAPoco("  * Poción de vida: " +  InventarioEnemigo[0], 5);}
        if ( InventarioEnemigo[1] > 0){imprimirPocoAPoco("  * Poción de agilidad: " +  InventarioEnemigo[1], 5);}
        if ( InventarioEnemigo[2] > 0){imprimirPocoAPoco("  * Poción de fuerza: " +  InventarioEnemigo[2], 5);}
        if ( InventarioEnemigo[3] > 0){imprimirPocoAPoco("  * Poción de resistencia: " +  InventarioEnemigo[3], 5);}
        if ( InventarioEnemigo[4] > 0){imprimirPocoAPoco("  * Poción de velocidad: " +  InventarioEnemigo[4], 5);}
        if ( InventarioEnemigo[5] > 0){imprimirPocoAPoco("  * Frasco de tormenta: " +  InventarioEnemigo[5], 5);}
        if ( InventarioEnemigo[6] > 0){imprimirPocoAPoco("  * Frasco de veneno: " +  InventarioEnemigo[6], 5);}
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        scanner.nextLine();
    }
    private static void EnemigoEspecificoBandido() {
        asignarInventarioEnemigo();
        EnemigoMagico = false;

        EnemigoActualNombre = "Bandido";
        Scanner scanner = new Scanner(System.in);
        SaludEnemigo = 55;
        DañoMinEnemigo = 5;
        DañoMaxEnemigo = DañoMinEnemigo + 6;
        AgilidadEnemigo = 4;
        ProbabilidadCriticoEnemigo = 0.18;
        System.out.println("");
        mostrarimagen("enemigobandido.txt");
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 2.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo))+ "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  * Inventario:"  + AMARILLO , 15);
        if ( InventarioEnemigo[0] > 0){imprimirPocoAPoco(AMARILLO + "  * Poción de vida: " +  InventarioEnemigo[0], 5);}
        if ( InventarioEnemigo[1] > 0){imprimirPocoAPoco("  * Poción de agilidad: " +  InventarioEnemigo[1], 5);}
        if ( InventarioEnemigo[2] > 0){imprimirPocoAPoco("  * Poción de fuerza: " +  InventarioEnemigo[2], 5);}
        if ( InventarioEnemigo[3] > 0){imprimirPocoAPoco("  * Poción de resistencia: " +  InventarioEnemigo[3], 5);}
        if ( InventarioEnemigo[4] > 0){imprimirPocoAPoco("  * Poción de velocidad: " +  InventarioEnemigo[4], 5);}
        if ( InventarioEnemigo[5] > 0){imprimirPocoAPoco("  * Frasco de tormenta: " +  InventarioEnemigo[5], 5);}
        if ( InventarioEnemigo[6] > 0){imprimirPocoAPoco("  * Frasco de veneno: " +  InventarioEnemigo[6], 5);}
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        scanner.nextLine();

    }
    private static void EnemigoEspecificoElfoJoven() {
        asignarInventarioEnemigo();
        EnemigoMagico = false;

        Scanner scanner = new Scanner(System.in);
        EnemigoActualNombre = "Elfo joven";
        SaludEnemigo = 55;
        DañoMinEnemigo = 5;
        DañoMaxEnemigo = DañoMinEnemigo + 6;
        AgilidadEnemigo = 4;
        ProbabilidadCriticoEnemigo = 0.18;
        System.out.println("");
        mostrarimagen("enemigoelfojoven.txt");
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 2.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  * Inventario:"  + AMARILLO , 15);
        if ( InventarioEnemigo[0] > 0){imprimirPocoAPoco(AMARILLO + "  * Poción de vida: " +  InventarioEnemigo[0], 5);}
        if ( InventarioEnemigo[1] > 0){imprimirPocoAPoco("  * Poción de agilidad: " +  InventarioEnemigo[1], 5);}
        if ( InventarioEnemigo[2] > 0){imprimirPocoAPoco("  * Poción de fuerza: " +  InventarioEnemigo[2], 5);}
        if ( InventarioEnemigo[3] > 0){imprimirPocoAPoco("  * Poción de resistencia: " +  InventarioEnemigo[3], 5);}
        if ( InventarioEnemigo[4] > 0){imprimirPocoAPoco("  * Poción de velocidad: " +  InventarioEnemigo[4], 5);}
        if ( InventarioEnemigo[5] > 0){imprimirPocoAPoco("  * Frasco de tormenta: " +  InventarioEnemigo[5], 5);}
        if ( InventarioEnemigo[6] > 0){imprimirPocoAPoco("  * Frasco de veneno: " +  InventarioEnemigo[6], 5);}
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        scanner.nextLine();

    }
    private static void EnemigoEspecificoDragonRojo() {
        asignarInventarioEnemigo();
        EnemigoMagico = false;

        Scanner scanner = new Scanner(System.in);
        EnemigoActualNombre = "Jefe Dragon Rojo";
        SaludEnemigo = 175;
        DañoMinEnemigo = 7;
        DañoMaxEnemigo = 25;
        AgilidadEnemigo = 5;
        ProbabilidadCriticoEnemigo = 0.21;
        System.out.println("");
        mostrarimagen("enemigodragonrojo.txt");
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 4.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  * Inventario:"  + AMARILLO , 15);
        if ( InventarioEnemigo[0] > 0){imprimirPocoAPoco(AMARILLO + "  * Poción de vida: " +  InventarioEnemigo[0], 5);}
        if ( InventarioEnemigo[1] > 0){imprimirPocoAPoco("  * Poción de agilidad: " +  InventarioEnemigo[1], 5);}
        if ( InventarioEnemigo[2] > 0){imprimirPocoAPoco("  * Poción de fuerza: " +  InventarioEnemigo[2], 5);}
        if ( InventarioEnemigo[3] > 0){imprimirPocoAPoco("  * Poción de resistencia: " +  InventarioEnemigo[3], 5);}
        if ( InventarioEnemigo[4] > 0){imprimirPocoAPoco("  * Poción de velocidad: " +  InventarioEnemigo[4], 5);}
        if ( InventarioEnemigo[5] > 0){imprimirPocoAPoco("  * Frasco de tormenta: " +  InventarioEnemigo[5], 5);}
        if ( InventarioEnemigo[6] > 0){imprimirPocoAPoco("  * Frasco de veneno: " +  InventarioEnemigo[6], 5);}
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        scanner.nextLine();

    }
    private static void Nivel_1() {
        int enemigoSeleccionado = (int) (Math.random() * 4);
        EnemigoMagico = false;

        EnemigoActualNombre = NombreEnemigoN1[enemigoSeleccionado];
        Scanner scanner = new Scanner(System.in);
        SaludEnemigo = (int) (Math.random() * 15) + 15;
        DañoMinEnemigo = (int)  (Math.random() * 3) + 2;
        DañoMaxEnemigo = DañoMinEnemigo + 5;
        AgilidadEnemigo = (int) (Math.random() * 3) + 1;
        ProbabilidadCriticoEnemigo = 0.09;

        System.out.println("");
        switch (enemigoSeleccionado){
            case 0:
                mostrarimagen("enemigoduende.txt");
                break;
            case 1:
                mostrarimagen("enemigolobosalvaje.txt");
                break;
            case 2:
                mostrarimagen("enemigogoblin.txt");
                break;
            case 3:
                mostrarimagen("enemigoratagigante.txt");
                break;
            default:
                ;
        }
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo." , 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 1.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        scanner.nextLine();
    }

    private static void EnemigoEspecificoGolemLava() {
        asignarInventarioEnemigo();
        EnemigoMagico = false;

        EnemigoActualNombre = "Golem de lava";
        Scanner scanner = new Scanner(System.in);
        SaludEnemigo = (int) (Math.random() * 20) + 40;
        DañoMinEnemigo = (int)  (Math.random() * 4) + 4;
        DañoMaxEnemigo = DañoMinEnemigo;
        AgilidadEnemigo = (int) (Math.random() * 3) + 1;
        ProbabilidadCriticoEnemigo = 0.25;
        System.out.println("");
        mostrarimagen("enemigogolemlava.txt");
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 2.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo))+ "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  * Inventario:" + AMARILLO , 15);
        if ( InventarioEnemigo[0] > 0){imprimirPocoAPoco("  * Poción de vida: " +  InventarioEnemigo[0], 5);}
        if ( InventarioEnemigo[1] > 0){imprimirPocoAPoco("  * Poción de agilidad: " +  InventarioEnemigo[1], 5);}
        if ( InventarioEnemigo[2] > 0){imprimirPocoAPoco("  * Poción de fuerza: " +  InventarioEnemigo[2], 5);}
        if ( InventarioEnemigo[3] > 0){imprimirPocoAPoco("  * Poción de resistencia: " +  InventarioEnemigo[3], 5);}
        if ( InventarioEnemigo[4] > 0){imprimirPocoAPoco("  * Poción de velocidad: " +  InventarioEnemigo[4], 5);}
        if ( InventarioEnemigo[5] > 0){imprimirPocoAPoco("  * Frasco de tormenta: " +  InventarioEnemigo[5], 5);}
        if ( InventarioEnemigo[6] > 0){imprimirPocoAPoco("  * Frasco de veneno: " +  InventarioEnemigo[6], 5);}
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        scanner.nextLine();
    }
    private static void Nivel_2() {
        asignarInventarioEnemigo();
        EnemigoMagico = false;
        int enemigoSeleccionado = (int) (Math.random() * 4);
        EnemigoActualNombre = NombreEnemigoN2[enemigoSeleccionado];
        Scanner scanner = new Scanner(System.in);
        SaludEnemigo = (int) (Math.random() * 25) + 30;
        DañoMinEnemigo = (int) (Math.random() * 5) + 4;
        DañoMaxEnemigo = (int) (Math.random() * 8) + DañoMinEnemigo ;
        EnemigoMagico = Math.random() < 0.25;
        AgilidadEnemigo = (int)  (Math.random() * 3) + 2;
        ProbabilidadCriticoEnemigo = 0.20;
        System.out.println("");

        switch (enemigoSeleccionado){
            case 0:
                mostrarimagen("enemigoesqueleto.txt");
                break;
            case 1:
                mostrarimagen("enemigoorco.txt");
                break;
            case 2:
                mostrarimagen("enemigoarpia.txt");
                break;
            case 3:
                mostrarimagen("golempiedra.txt");
                break;
            default:
                ;
        }
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 2.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        if (EnemigoMagico){ imprimirPocoAPoco("  * Daño mágico: 3 - " + (DañoMinEnemigo + 3) , 5);}
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo))+ "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo , 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  * Inventario:"  + AMARILLO , 15);
        if ( InventarioEnemigo[0] > 0){imprimirPocoAPoco(AMARILLO + "  * Poción de vida: " +  InventarioEnemigo[0], 5);}
        if ( InventarioEnemigo[1] > 0){imprimirPocoAPoco("  * Poción de agilidad: " +  InventarioEnemigo[1], 5);}
        if ( InventarioEnemigo[2] > 0){imprimirPocoAPoco("  * Poción de fuerza: " +  InventarioEnemigo[2], 5);}
        if ( InventarioEnemigo[3] > 0){imprimirPocoAPoco("  * Poción de resistencia: " +  InventarioEnemigo[3], 5);}
        if ( InventarioEnemigo[4] > 0){imprimirPocoAPoco("  * Poción de velocidad: " +  InventarioEnemigo[4], 5);}
        if ( InventarioEnemigo[5] > 0){imprimirPocoAPoco("  * Frasco de tormenta: " +  InventarioEnemigo[5], 5);}
        if ( InventarioEnemigo[6] > 0){imprimirPocoAPoco("  * Frasco de veneno: " +  InventarioEnemigo[6], 5);}
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        scanner.nextLine();

    }
    private static void Nivel_3() {
        asignarInventarioEnemigo();
        EnemigoMagico = false;
        int enemigoSeleccionado = (int) (Math.random() * 4);
        EnemigoActualNombre = NombreEnemigoN3[enemigoSeleccionado];
        Scanner scanner = new Scanner(System.in);
        SaludEnemigo = (int) (Math.random() * 60) + 40;
        DañoMinEnemigo = (int)  (Math.random() * 5) + 5;
        DañoMaxEnemigo =  (int) (Math.random() * 9) + DañoMinEnemigo ;
        EnemigoMagico = Math.random() < 0.41;
        AgilidadEnemigo =  (int) (Math.random() * 4) + 3;
        ProbabilidadCriticoEnemigo = 0.26;

        System.out.println("");
        switch (enemigoSeleccionado){
            case 0:
                mostrarimagen("enemigoesqueletoguerrero.txt");
                break;
            case 1:
                mostrarimagen("enemigoliche.txt");
                break;
            case 2:
                mostrarimagen("enemigodemoniomayor.txt");
                break;
            case 3:
                mostrarimagen("enemigohidra.txt");
                break;
            default:
                ;
        }
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  Atributos del enemigo.", 15);
        imprimirPocoAPoco("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        System.out.println("");
        imprimirPocoAPoco("  * El enemigo es: " + EnemigoActualNombre + " de nivel 3.", 10);
        imprimirPocoAPoco(AMARILLO + "  * Salud: " + SaludEnemigo, 5);
        imprimirPocoAPoco("  * Daño: " + DañoMinEnemigo + " - " + DañoMaxEnemigo, 5);
        if (EnemigoMagico){ imprimirPocoAPoco("  * Daño mágico: 3 - " + (DañoMinEnemigo + 3) , 5);}
        imprimirPocoAPoco("  * Probabilidad critico: " + ((int) (100 * ProbabilidadCriticoEnemigo)) + "%", 5);
        imprimirPocoAPoco("  * Agilidad: " + AgilidadEnemigo + RESET, 5);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  * Inventario:"  + AMARILLO , 15);
        if ( InventarioEnemigo[0] > 0){imprimirPocoAPoco(AMARILLO + "  * Poción de vida: " +  InventarioEnemigo[0], 5);}
        if ( InventarioEnemigo[1] > 0){imprimirPocoAPoco("  * Poción de agilidad: " +  InventarioEnemigo[1], 5);}
        if ( InventarioEnemigo[2] > 0){imprimirPocoAPoco("  * Poción de fuerza: " +  InventarioEnemigo[2], 5);}
        if ( InventarioEnemigo[3] > 0){imprimirPocoAPoco("  * Poción de resistencia: " +  InventarioEnemigo[3], 5);}
        if ( InventarioEnemigo[4] > 0){imprimirPocoAPoco("  * Poción de velocidad: " +  InventarioEnemigo[4], 5);}
        if ( InventarioEnemigo[5] > 0){imprimirPocoAPoco("  * Frasco de tormenta: " +  InventarioEnemigo[5], 5);}
        if ( InventarioEnemigo[6] > 0){imprimirPocoAPoco("  * Frasco de veneno: " +  InventarioEnemigo[6], 5);}
        System.out.println("");
        imprimirPocoAPoco(ROJO + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~", 3);
        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar... " + RESET, 15);
        scanner.nextLine();

    }

    private static int dañoBase = 0;
    private static void calcularDañoEnemigo() {
        Random random = new Random();
        boolean Ataque = false;
        dañoBase = 0;
        DañoMagicoEnemigoSN = false;

        if (EnemigoMagico) {
            Ataque = Math.random() < 0.41;
        }


        if (Ataque){
            DañoMagicoEnemigoSN = true;
            DañoMagicoEnemigo =  (int) (Math.random() * DañoMinEnemigo ) + 3;

        }
        else {

            int ReducionPorVarita = 0;
            if (DañoVarita[3] > 0) {
                ReducionPorVarita = 3;
                imprimirPocoAPoco(VERDE + "  * El daño del enemigo es debilitado: -" + ReducionPorVarita + " daño" + RESET, 15);
            }
            dañoBase = (int) (Math.random() * ((DañoMaxEnemigo + DañoEnemigoPocion) - DañoMinEnemigo + 1)) + (DañoMinEnemigo - ReducionPorVarita) ;

            if (dañoBase < 0) {
                dañoBase = 0;
            }

            boolean esCritico = Math.random() < ProbabilidadCriticoEnemigo;
            if (esCritico) {
                imprimirPocoAPoco(ROJO + "  ¡Golpe crítico del enemigo! +50% daño infligido." + RESET, 15);
                imprimirPocoAPoco(AMARILLO + "  * Daño inicial: " + dañoBase, 15);
                dañoBase *= 1.5;

            }
        }


    }
    private static void calcularDañoJugador() {
        if (CriticoEspada && ContadorDestreza[0] > 0){
            DañoTotal =  DañoMaxJugador;
            boolean esCritico = Math.random() < (ProbabilidadCriticoJugador + 0.2) ;
            if (esCritico) {
                imprimirPocoAPoco(ROJO + "  ->> ¡Golpe crítico del jugador! +200% daño infligido." + RESET, 15);
                imprimirPocoAPoco(VERDE + "  * Daño inicial: " + DañoTotal, 15);
                    DañoTotal *= 3;
            }
        }
        else if (CriticoEspada){
            DañoTotal = (int) (Math.random() * (DañoMaxJugador - DañoMinJugador + 1)) + DañoMinJugador;
            boolean esCritico = Math.random() <  (ProbabilidadCriticoJugador + 0.2);
            if (esCritico) {
                imprimirPocoAPoco(ROJO + "  ->> ¡Golpe crítico del jugador! +150% daño infligido." + RESET, 15);
                imprimirPocoAPoco(VERDE + "  * Daño inicial: " + DañoTotal, 15);
                    DañoTotal *= 2.5;


            }
            CriticoEspada = false;
        }
        else {
            DañoTotal = (int) (Math.random() * (DañoMaxJugador - DañoMinJugador + 1)) + DañoMinJugador;

            boolean esCritico = Math.random() < ProbabilidadCriticoJugador;
            if (ContadorDestreza[0] > 0){
                DañoTotal = DañoMaxJugador;
            }
            if (esCritico) {
                imprimirPocoAPoco(ROJO + "  ¡Golpe crítico del jugador! +100%  daño infligido." + RESET, 15);
                imprimirPocoAPoco(VERDE + "  * Daño inicial: " + DañoTotal, 15);
                    DañoTotal *= 2; // Doble daño en caso de crítico


            }
        }
    }
    private static void calcularDañoTotalJugador() {
        if (ClaseMago) {
            ReduccionDañoEspada = (int) (Daño[1] * PorcentajeReduccionDañoEspada);
            DañoMaxJugador = ReduccionDañoEspada + Daño[0] + Daño[2] + FuerzaBaston;
        } else if (ClaseInvocador) {
            ReduccionDañoEspada = (int) (Daño[1] * PorcentajeReduccionDañoEspada);
            DañoMaxJugador = Daño[0] + ReduccionDañoEspada + Daño[2];
        } else if (ClaseGuerrero) {
            ReduccionDañoEspada = (int) (Daño[1] * PorcentajeReduccionDañoEspada);
            DañoMaxJugador = Daño[0] + Daño[1] + Daño[2];
        }

        DañoMinJugador = DañoMaxJugador - 5;

        if (DañoMinJugador <= 0) {
            DañoMinJugador = 0;
            if (!ClaseInvocador) {
                DañoMinJugador = 1;
            }
        }
    }
    private static void calcularDañoInvocacion() {
        DañoInvocado = (int) (Math.random() * ((DañoMaxInvocacion + Daño[3]) - DañoMinInvocacion + 1)) + DañoMinInvocacion;

        boolean esCritico = Math.random() < ProbabilidadCriticoInvocacion;
        //Aumentamos al daño maximo si la pocion esta activa
        if (ContadorDestreza[1] > 0) {
            DañoInvocado = (DañoMaxInvocacion + Daño[3]);
        }
        if (esCritico) {
            imprimirPocoAPoco(ROJO + "  ¡Golpe crítico de la invocación! +40% daño infligido." + RESET, 15);
            imprimirPocoAPoco(VERDE + "  * Daño inicial: " + DañoInvocado, 15);
            DañoInvocado *= 1.4; // Doble daño en caso de crítico

        }
    }

    private static void VerificacionVaritas() {


        if (VaritaActiva[0] == true || VaritaActiva[1] == true || VaritaActiva[2] == true || VaritaActiva[3] == true || VaritaActiva[4] == true || VaritaActiva[5] == true) {


            if (VaritaActiva[0] == true) {
                varitasPoseidas.add(0);

            }
            if (VaritaActiva[1] == true) {

                varitasPoseidas.add(1);
            }
            if (VaritaActiva[2] == true) {

                varitasPoseidas.add(2);
            }
            if (VaritaActiva[3] == true) {

                varitasPoseidas.add(3);
            }
            if (VaritaActiva[4] == true) {

                varitasPoseidas.add(4);
            }
            if (VaritaActiva[5] == true) {

                varitasPoseidas.add(5);
            }
        }

    }
    private static void elegirVarita() {
        Scanner scanner = new Scanner(System.in);
        if (VaritaActiva[0] == true || VaritaActiva[1] == true || VaritaActiva[2] == true || VaritaActiva[3] == true || VaritaActiva[4] == true || VaritaActiva[5] == true) {
            mostrarVaritas();
            System.out.print(AZUL + "  Elige una varita para usar (0 para salir):" + RESET);

            if (scanner.hasNextInt()) {
                int eleccion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea

                System.out.println("");
                if (eleccion == 0) {
                    System.out.println(MAGENTA + "                                                      *_*" + RESET);
                    return;
                }

                // Verificar que la elección esté dentro del rango de varitas disponibles
                if (eleccion < 1 || eleccion > varitasPoseidas.size()) {
                    System.out.println(ROJO + "  ¡Que mal!, opción no disponible." + RESET);
                    System.out.println("");
                    return; // Salir del método si la elección no es válida
                }


                System.out.println("");
                int indexVarita = varitasPoseidas.get(eleccion - 1);

                // Ejecuta la función correspondiente a la varita elegida
                switch (indexVarita) {
                    case 0:
                        if (EnergiaVarita[0] >= 1) {
                            BastonMago();
                        } else {
                            System.out.println(ROJO + "La varita no tiene suficiente energía." + RESET);
                        }
                        break;
                    case 1:
                        if (EnergiaVarita[1] >= 1) {
                            VaritaFuego();
                        } else {
                            System.out.println(ROJO + "La varita no tiene suficiente energía." + RESET);
                        }
                        break;
                    case 2:
                        if (EnergiaVarita[2] >= 1) {
                            VaritaHielo();
                        } else {
                            System.out.println(ROJO + "La varita no tiene suficiente energía." + RESET);
                        }
                        break;
                    case 3:
                        if (EnergiaVarita[3] >= 1) {
                            VaritaDebilidad();
                        } else {
                            System.out.println(ROJO + "La varita no tiene suficiente energía." + RESET);
                        }
                        break;
                    case 4:

                        if (EnergiaVarita[4] >= 1) {
                            VaritaCuración();
                        } else {
                            System.out.println(ROJO + "La varita no tiene suficiente energía." + RESET);
                        }
                        break;
                    case 5:
                        if (EnergiaVarita[5] >= 1) {
                            VaritaProtección();
                        } else {
                            System.out.println(ROJO + "La varita no tiene suficiente energía." + RESET);
                        }
                        break;
                    default:
                        System.out.println(ROJO + "Opción no válida." + RESET);
                }
            }else {
                scanner.nextLine();  // Consumir la entrada no numérica
                imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número." + RESET, 10);
                System.out.println("  Cargando...");
                imprimirPocoAPoco(" . . .", 400);

                elegirVarita();
            }
        }
        else {
            System.out.println(BLANCO + "  * No hay varitas disponibles" + RESET);
        }
    }
    private static void PiedraRecargarVarita() {
        Scanner scanner = new Scanner(System.in);
        if (VaritaActiva[0] == true || VaritaActiva[1] == true || VaritaActiva[2] == true || VaritaActiva[3] == true || VaritaActiva[4] == true || VaritaActiva[5] == true) {
            imprimirPocoAPoco(RESET + "-------------------------------------------------------------------------------------", 2);
            mostrarVaritas();
            imprimirPocoAPoco(RESET + "-------------------------------------------------------------------------------------", 2);
            imprimirPocoAPoco(AZUL + "  Elige una varita para recargar: " + RESET, 10);

            if (scanner.hasNextInt()) {
                int eleccion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea


                System.out.println("");

                // Verificar que la elección esté dentro del rango de varitas disponibles
                if (eleccion < 1 || eleccion > varitasPoseidas.size()) {
                    imprimirPocoAPoco(ROJO + "  ¡*! Opción no válida, pierdes el objeto ¡*!" + RESET, 10);
                    return; // Salir del método si la elección no es válida
                }


                System.out.println("");
                int indexVarita = varitasPoseidas.get(eleccion - 1);

                // Ejecuta la función correspondiente a la varita elegida
                switch (indexVarita) {
                    case 0:
                        EnergiaVarita[0] += 4;
                        imprimirPocoAPoco(MAGENTA + "  El bastón es recargado con éxito + 4 de energía." + RESET, 10);
                        if (EnergiaVarita[0] > MaxEnergiaVarita[0]) {
                            EnergiaVarita[0] = MaxEnergiaVarita[0];
                            imprimirPocoAPoco(CYAN + "  *! La energía otorgada supera la energía máxima del bastón, el exceso de energía se ve negada." + RESET, 10);
                        }
                        break;
                    case 1:
                        EnergiaVarita[1] += 2;
                        imprimirPocoAPoco(MAGENTA + "  La varita de fuego es recargado con éxito + 2 de energía." + RESET, 10);
                        if (EnergiaVarita[1] > MaxEnergiaVarita[1]) {
                            EnergiaVarita[1] = MaxEnergiaVarita[1];
                            imprimirPocoAPoco(CYAN + "  *! La energía otorgada supera la energía máxima de la varita de fuego, el exceso de energía se ve negada." + RESET, 10);
                        }
                        break;
                    case 2:
                        EnergiaVarita[2] += 2;
                        imprimirPocoAPoco(MAGENTA + "  La varita de hielo es recargado con éxito + 2 de energía." + RESET, 10);
                        if (EnergiaVarita[2] > MaxEnergiaVarita[2]) {
                            EnergiaVarita[2] = MaxEnergiaVarita[2];
                            imprimirPocoAPoco(CYAN + "  *! La energía otorgada supera la energía máxima de la varita de hielo, el exceso de energía se ve negada." + RESET, 10);
                        }
                        break;
                    case 3:
                        EnergiaVarita[3] += 2;
                        imprimirPocoAPoco(MAGENTA + "  La varita de debilidad es recargado con éxito + 2 de energía." + RESET, 10);
                        if (EnergiaVarita[3] > MaxEnergiaVarita[3]) {
                            EnergiaVarita[3] = MaxEnergiaVarita[3];
                            imprimirPocoAPoco(CYAN + "  *! La energía otorgada supera la energia máxima de la varita de debilidad, el exceso de energia se ve negada." + RESET, 10);
                        }
                        break;
                    case 4:
                        EnergiaVarita[4] += 2;
                        imprimirPocoAPoco(MAGENTA + "  La varita de curación es recargado con éxito + 2 de energía." + RESET, 10);
                        if (EnergiaVarita[4] > MaxEnergiaVarita[4]) {
                            EnergiaVarita[4] = MaxEnergiaVarita[4];
                            imprimirPocoAPoco(CYAN + "  *! La energía otorgada supera la energía máxima de la varita de curación, el exceso de energia se ve negada." + RESET, 10);
                        }
                        break;
                    case 5:
                        EnergiaVarita[5] += 1;
                        imprimirPocoAPoco(MAGENTA + "  La varita de protección es recargado con éxito + 2 de energía." + RESET, 10);
                        if (EnergiaVarita[5] > MaxEnergiaVarita[5]) {
                            EnergiaVarita[5] = MaxEnergiaVarita[5];
                            imprimirPocoAPoco(CYAN + "  *! La energía otorgada supera la energia máxima de la varita de protección, el exceso de energia se ve negada." + RESET, 10);
                        }
                        break;
                    default:
                        imprimirPocoAPoco(ROJO + "  ¡*! Opción no válida, pierdes el objeto ¡*!" + RESET, 10);
                        break;
                }
            } else {
                scanner.nextLine();  // Consumir la entrada no numérica
                imprimirPocoAPoco(AMARILLO + "  Ups, vuelve a intentar con un número." + RESET, 10);
                System.out.println("  Cargando...");
                imprimirPocoAPoco(" . . .", 400);

                PiedraRecargarVarita();
            }

        }
        else {
            Cantidades[5] += 1;
            imprimirPocoAPoco(AMARILLO + "  *! No posees varitas a recargar." + RESET, 10);
            imprimirPocoAPoco(CYAN + "  -> Tu cristal de mana, se ve preservada." + RESET, 10);
        }

    }

    private static void BastonMago_Nivel_1() {
        DañoVarita[0] = 6;
        Scanner scanner = new Scanner(System.in);
        imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 10);
        imprimirPocoAPoco("  Una herramienta particular...", 5);
        imprimirPocoAPoco("  Con la capacidad de hacer diferentes hechizos...", 5);
        imprimirPocoAPoco(VERDE + "  1. Dañar al enemigo, +6 daño a enemigo," + RESET + " -3 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  2. Aumentar tu agilidad en +2," + RESET + " -1 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  3. Aumentar tu fuerza en +5," + RESET + " -2 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  4. Obtener +7 de escudo mágico," + RESET + " -3 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  5. Sanar tus heridas +5 de salud," + RESET + " -2 de energía." + RESET, 3);
        imprimirPocoAPoco(AZUL + "  Elige la acción a realizar: " + RESET, 10);
        String opcion = scanner.nextLine();

        System.out.println("");

        switch (opcion) {
            case "1":
                if (EnergiaVarita[0] >= 3) {
                    EnergiaVarita[0] -= 3;
                    int dañovarita = 0;
                    dañovarita = DañoVarita[0];
                    if (ContadorResistencia[2] > 0){
                        dañovarita *= 0.5;
                        imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + dañovarita, 15);
                    }
                    SaludEnemigo -= dañovarita;

                    imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo: " + dañovarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "2":
                if (EnergiaVarita[0] >= 1) {
                    AgilidadBaston += 2;
                    if (AgilidadBaston > 2) {
                        AgilidadBaston = 2;
                        imprimirPocoAPoco(ROJO + "  Este tipo de magia ya estaba activo, solo durara más tiempo." + RESET, 10);
                    }
                    ContadorAgilidadBaston = 0;
                    EnergiaVarita[0] -= 1;
                    imprimirPocoAPoco(MAGENTA + "  * Tu agilidad aumenta en +" + AgilidadBaston + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "3":
                if (EnergiaVarita[0] >= 2) {
                    FuerzaBaston += 5;
                    if (FuerzaBaston > 5) {
                        FuerzaBaston = 5;
                        imprimirPocoAPoco(ROJO + "  Este tipo de magia ya estaba activo, solo durara más tiempo." + RESET, 10);
                    }
                    ContadorFuerzaBaston = 0;
                    EnergiaVarita[0] -= 2;
                    imprimirPocoAPoco(MAGENTA + "  * Tu fuerza aumenta en +" + FuerzaBaston + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "4":
                if (EnergiaVarita[0] >= 3) {
                    Salud[4] += 7;
                    EnergiaVarita[0] -= 3;
                    imprimirPocoAPoco(MAGENTA + "  * Obtienes un escudo mágico que te protege +7 de escudo." + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "5":
                if (EnergiaVarita[0] >= 2) {
                    Salud[0] += 5;
                    EnergiaVarita[0] -= 2;

                    imprimirPocoAPoco(MAGENTA + "  * Sanas mágicamente y tus heridas se recuperan un poco +5 de salud." + RESET, 10);
                    if (Salud[0] > SaludMax[0]) {
                        Salud[0] = SaludMax[0];
                        imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 10);
                        imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
                    }
                    imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            default:
                imprimirPocoAPoco(ROJO + "  ¡Intentalo de nuevo!" + RESET, 10);
                scanner.nextLine();
                BastonMago_Nivel_1();
                break;
        }

    }
    private static void BastonMago_Nivel_2() {
        DañoVarita[0] = 10;
        Scanner scanner = new Scanner(System.in);
        imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 10);
        imprimirPocoAPoco("  Una herramienta particular...", 5);
        imprimirPocoAPoco("  Con la capacidad de hacer diferentes hechizos...", 5);
        imprimirPocoAPoco(VERDE + "  1. Dañar al enemigo, +10 daño a enemigo," + RESET + " -3 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  2. Aumentar tu agilidad en +3," + RESET + " -1 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  3. Aumentar tu fuerza en +7," + RESET + " -2 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  4. Obtener +15 de escudo mágico," + RESET + " -4 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  5. Sanar tus heridas +7 de salud," + RESET + " -2 de energía." + RESET, 3);
        imprimirPocoAPoco(AZUL + "  Elige la acción a realizar: " + RESET, 10);
        String opcion = scanner.nextLine();

        System.out.println("");

        switch (opcion) {
            case "1":
                if (EnergiaVarita[0] >= 3) {
                    EnergiaVarita[0] -= 3;
                    int dañovarita = 0;
                    dañovarita = DañoVarita[0];
                    if (ContadorResistencia[2] > 0){
                        dañovarita *= 0.5;
                        imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + dañovarita, 15);
                    }
                    SaludEnemigo -= dañovarita;

                    imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo: " + dañovarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "2":
                if (EnergiaVarita[0] >= 1) {
                    AgilidadBaston += 3;
                    if (AgilidadBaston > 3) {
                        AgilidadBaston = 3;
                        imprimirPocoAPoco(ROJO + "  Este tipo de magia ya estaba activo, solo durara más tiempo." + RESET, 10);
                    }
                    ContadorAgilidadBaston = 0;
                    EnergiaVarita[0] -= 1;
                    imprimirPocoAPoco(MAGENTA + "  * Tu agilidad aumenta en +" + AgilidadBaston + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "3":
                if (EnergiaVarita[0] >= 2) {
                    FuerzaBaston += 7;
                    if (FuerzaBaston > 7) {
                        FuerzaBaston = 7;
                        imprimirPocoAPoco(ROJO + "  Este tipo de magia ya estaba activo, solo durara más tiempo." + RESET, 10);
                    }
                    ContadorFuerzaBaston = 0;
                    EnergiaVarita[0] -= 2;
                    imprimirPocoAPoco(MAGENTA + "  * Tu fuerza aumenta en +" + FuerzaBaston + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "4":
                if (EnergiaVarita[0] >= 4) {
                    Salud[4] += 15;
                    EnergiaVarita[0] -= 4;
                    imprimirPocoAPoco(MAGENTA + "  * Obtienes un escudo mágico que te protege +15 de escudo." + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "5":
                if (EnergiaVarita[0] >= 2) {
                    Salud[0] += 7;
                    EnergiaVarita[0] -= 2;

                    imprimirPocoAPoco(MAGENTA + "  * Sanas mágicamente y tus heridas se recuperan un poco +7 de salud." + RESET, 10);
                    if (Salud[0] > SaludMax[0]) {
                        Salud[0] = SaludMax[0];
                        imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 10);
                        imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
                    }
                    imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            default:
                imprimirPocoAPoco(ROJO + "  ¡Intentalo de nuevo!" + RESET, 10);
                scanner.nextLine();
                BastonMago_Nivel_2();
                break;
        }

    }
    private static void BastonMago_Nivel_3() {
        DañoVarita[0] = 15;
        Scanner scanner = new Scanner(System.in);
        imprimirPocoAPoco(AMARILLO + "  Descripción:" + RESET, 10);
        imprimirPocoAPoco("  El bastón del mago es una herramienta mística...", 5);
        imprimirPocoAPoco("  Capaz de realizar varios hechizos poderosos:", 5);
        imprimirPocoAPoco(VERDE + "  1. Dañar al enemigo, +12 daño a enemigo y Sanar tus heridas +6 de salud" + RESET + " -4 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  2. Aumentar tu agilidad en +3 y tu fuerza en +7," + RESET + " -3 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  3. Obtener +20 de escudo mágico," + RESET + " -4 de energía.", 3);
        imprimirPocoAPoco(VERDE + "  4. Exploción mágica," + AMARILLO + " usa toda la energía." + RESET, 3);
        imprimirPocoAPoco(AZUL + "  Elige la acción a realizar: " + RESET, 10);
        String opcion = scanner.nextLine();

        System.out.println("");

        switch (opcion) {
            case "1":
                if (EnergiaVarita[0] >= 4) {
                    EnergiaVarita[0] -= 4;
                    int dañovarita = 0;
                    dañovarita = DañoVarita[0];
                    if (ContadorResistencia[2] > 0){
                        dañovarita *= 0.5;
                        imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + dañovarita, 15);
                    }
                    SaludEnemigo -= dañovarita;

                    imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo: " + dañovarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 10);
                    Salud[0] += 6;
                    imprimirPocoAPoco(MAGENTA + "  * Salud: +6" + RESET, 10);
                    if (Salud[0] > SaludMax[0]) {
                        Salud[0] = SaludMax[0];
                        imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 10);
                        imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
                    }
                    imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "2":
                if (EnergiaVarita[0] >= 3) {
                    EnergiaVarita[0] -= 3;

                    AgilidadBaston += 3;
                    if (AgilidadBaston > 3) {
                        AgilidadBaston = 3;
                    }
                    ContadorAgilidadBaston = 0;

                    FuerzaBaston += 7;
                    if (FuerzaBaston > 7) {
                        FuerzaBaston = 7;
                    }
                    ContadorFuerzaBaston = 0;

                    imprimirPocoAPoco(MAGENTA + "  * Agilidad por bastón: " + AgilidadBaston + RESET, 10);
                    imprimirPocoAPoco(MAGENTA + "  * Fuerza por bastón:  +" + FuerzaBaston + RESET, 10);
                    System.out.println("");


                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "3":
                if (EnergiaVarita[0] >= 4) {
                    Salud[4] += 20;
                    EnergiaVarita[0] -= 4;
                    imprimirPocoAPoco(MAGENTA + "  * Escudo mágico: +20" + RESET, 10);
                    System.out.println("");
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 10);
                }
                break;
            case "4":

                if (EnergiaVarita[0] >= 1) {
                    //-------------------------------------------------------------------------------------------------------------------

                    imprimirPocoAPoco(MAGENTA + "  * Un incremento de mágia caotica y sin control." + RESET, 10);
                    if (EnergiaVarita[0] <= 15 && EnergiaVarita[0] > 12){
                        // Generar los dos hechizos aleatorios
                        int primerHechizo = (int) (Math.random() * 5) + 1;
                        int segundoHechizo;
                        do {
                            segundoHechizo = (int) (Math.random() * 5) + 1;
                        } while (segundoHechizo == primerHechizo);

// Primer switch para el primer hechizo
                        switch (primerHechizo) {
                            case 1:
                                AgilidadBaston += 6;
                                if (AgilidadBaston > 6) {
                                    AgilidadBaston = 6;
                                }
                                ContadorAgilidadBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Agilidad por bastón: " + AgilidadBaston + RESET, 10);
                                break;
                            case 2:
                                FuerzaBaston += 10;
                                if (FuerzaBaston > 10) {
                                    FuerzaBaston = 10;
                                }
                                ContadorFuerzaBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Fuerza por bastón: " + FuerzaBaston + RESET, 10);
                                break;
                            case 3:
                                int dañovarita = 0;
                                dañovarita = (2 * DañoVarita[0]) + 5;
                                if (ContadorResistencia[2] > 0){
                                    dañovarita *= 0.5;
                                    imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + dañovarita, 15);
                                }
                                SaludEnemigo -= dañovarita;
                                imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo: " + dañovarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 10);

                                break;
                            case 4:
                                Salud[0] += 25;
                                imprimirPocoAPoco(MAGENTA + "  * Salud: +25" + RESET, 10);
                                if (Salud[0] > SaludMax[0]) {
                                    Salud[0] = SaludMax[0];
                                    imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 10);
                                    imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
                                }
                                imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);
                                break;
                            case 5:
                                Salud[4] += 40;
                                imprimirPocoAPoco(MAGENTA + "  * Escudo mágico: +40" + RESET, 10);
                                break;
                        }

// Segundo switch para el segundo hechizo
                        switch (segundoHechizo) {
                            case 1:
                                AgilidadBaston += 6;
                                if (AgilidadBaston > 6) {
                                    AgilidadBaston = 6;
                                }
                                ContadorAgilidadBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Agilidad por bastón: " + AgilidadBaston + RESET, 10);
                                break;
                            case 2:
                                FuerzaBaston += 10;
                                if (FuerzaBaston > 10) {
                                    FuerzaBaston = 10;
                                }
                                ContadorFuerzaBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Fuerza por bastón: " + FuerzaBaston + RESET, 10);
                                break;
                            case 3:
                                int dañovarita = 0;
                                dañovarita = (2 * DañoVarita[0]) + 5;
                                if (ContadorResistencia[2] > 0){
                                    dañovarita *= 0.5;
                                    imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + dañovarita, 15);
                                }
                                SaludEnemigo -= dañovarita;
                                imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo: " + dañovarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 10);
                                break;
                            case 4:
                                Salud[0] += 25;
                                imprimirPocoAPoco(MAGENTA + "  * Salud: +25" + RESET, 10);
                                if (Salud[0] > SaludMax[0]) {
                                    Salud[0] = SaludMax[0];
                                    imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 10);
                                    imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
                                }
                                imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);
                                break;
                            case 5:
                                Salud[4] += 40;
                                imprimirPocoAPoco(MAGENTA + "  * Escudo mágico: +40" + RESET, 10);
                                break;
                        }


                    }
                    else  if (EnergiaVarita[0] <= 12 && EnergiaVarita[0] > 5){
                        // Generar los dos hechizos aleatorios
                        int primerHechizo = (int) (Math.random() * 5) + 1;
                        int segundoHechizo;
                        do {
                            segundoHechizo = (int) (Math.random() * 5) + 1;
                        } while (segundoHechizo == primerHechizo);

                            // Primer switch para el primer hechizo
                        switch (primerHechizo) {
                            case 1:
                                AgilidadBaston += 4;
                                if (AgilidadBaston > 4) {
                                    AgilidadBaston = 4;
                                }
                                ContadorAgilidadBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Agilidad por bastón: " + AgilidadBaston + RESET, 10);
                                break;
                            case 2:
                                FuerzaBaston += 7;
                                if (FuerzaBaston > 7) {
                                    FuerzaBaston = 7;
                                }
                                ContadorFuerzaBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Fuerza por bastón: " + FuerzaBaston + RESET, 10);
                                break;
                            case 3:
                                int dañovarita = 0;
                                dañovarita = DañoVarita[0];
                                if (ContadorResistencia[2] > 0){
                                    dañovarita *= 0.5;
                                    imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + dañovarita, 15);
                                }
                                SaludEnemigo -= dañovarita;
                                imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo: " + dañovarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 10);
                                break;
                            case 4:
                                Salud[0] += 15;
                                imprimirPocoAPoco(MAGENTA + "  * Salud: +15" + RESET, 10);
                                if (Salud[0] > SaludMax[0]) {
                                    Salud[0] = SaludMax[0];
                                    imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 10);
                                    imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
                                }
                                imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);
                                break;
                            case 5:
                                Salud[4] += 25;
                                imprimirPocoAPoco(MAGENTA + "  * Escudo mágico: +25" + RESET, 10);
                                break;
                        }

// Segundo switch para el segundo hechizo
                        switch (segundoHechizo) {
                            case 1:
                                AgilidadBaston += 4;
                                if (AgilidadBaston > 4) {
                                    AgilidadBaston = 4;
                                }
                                ContadorAgilidadBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Agilidad por bastón: " + AgilidadBaston + RESET, 10);
                                break;
                            case 2:
                                FuerzaBaston += 7;
                                if (FuerzaBaston > 7) {
                                    FuerzaBaston = 7;
                                }
                                ContadorFuerzaBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Fuerza por bastón: " + FuerzaBaston + RESET, 10);
                                break;
                            case 3:
                                int dañovarita = 0;
                                dañovarita = DañoVarita[0];
                                if (ContadorResistencia[2] > 0){
                                    dañovarita *= 0.5;
                                    imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + dañovarita, 15);
                                }
                                SaludEnemigo -= dañovarita;
                                imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo: " + dañovarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 10);
                                break;
                            case 4:
                                Salud[0] += 15;
                                imprimirPocoAPoco(MAGENTA + "  * Salud: +15" + RESET, 10);
                                if (Salud[0] > SaludMax[0]) {
                                    Salud[0] = SaludMax[0];
                                    imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 10);
                                    imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
                                }
                                imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 10);
                                break;
                            case 5:
                                Salud[4] += 25;
                                imprimirPocoAPoco(MAGENTA + "  * Escudo mágico: +25" + RESET, 10);
                                break;
                        }


                    }
                    else{
                        // Generar los dos hechizos aleatorios
                        int primerHechizo = (int) (Math.random() * 5) + 1;
                        int segundoHechizo;
                        do {
                            segundoHechizo = (int) (Math.random() * 5) + 1;
                        } while (segundoHechizo == primerHechizo);

                            // Primer switch para el primer hechizo
                        switch (primerHechizo) {
                            case 1:
                                AgilidadBaston += 2;
                                if (AgilidadBaston > 2) {
                                    AgilidadBaston = 2;
                                }
                                ContadorAgilidadBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Agilidad por bastón: " + AgilidadBaston + RESET, 10);
                                break;
                            case 2:
                                FuerzaBaston += 4;
                                if (FuerzaBaston > 4) {
                                    FuerzaBaston = 4;
                                }
                                ContadorFuerzaBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Fuerza por bastón: " + FuerzaBaston + RESET, 20);
                                break;
                            case 3:
                                int dañovarita = 0;
                                dañovarita = (DañoVarita[0] / 2) + 3;
                                if (ContadorResistencia[2] > 0){
                                    dañovarita *= 0.5;
                                    imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + dañovarita, 15);
                                }
                                SaludEnemigo -= dañovarita;
                                imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo: " + dañovarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 10);
                                break;
                            case 4:
                                Salud[0] += 10;
                                imprimirPocoAPoco(MAGENTA + "  * Salud: +10" + RESET, 20);
                                if (Salud[0] > SaludMax[0]) {
                                    Salud[0] = SaludMax[0];
                                    imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 15);
                                    imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 15);
                                }
                                imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 20);
                                break;
                            case 5:
                                Salud[4] += 15;
                                imprimirPocoAPoco(MAGENTA + "  * Escudo mágico: +15" + RESET, 20);
                                break;
                        }

                            // Segundo switch para el segundo hechizo
                        switch (segundoHechizo) {
                            case 1:
                                AgilidadBaston += 2;
                                if (AgilidadBaston > 2) {
                                    AgilidadBaston = 2;
                                }
                                ContadorAgilidadBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Agilidad por bastón: " + AgilidadBaston + RESET, 10);
                                break;
                            case 2:
                                FuerzaBaston += 4;
                                if (FuerzaBaston > 4) {
                                    FuerzaBaston = 4;
                                }
                                ContadorFuerzaBaston = 0;
                                imprimirPocoAPoco(MAGENTA + "  * Fuerza por bastón: " + FuerzaBaston + RESET, 20);
                                break;
                            case 3:
                                int dañovarita = 0;
                                dañovarita = (DañoVarita[0] / 2) + 3;
                                if (ContadorResistencia[2] > 0){
                                    dañovarita *= 0.5;
                                    imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + dañovarita, 15);
                                }
                                SaludEnemigo -= dañovarita;
                                imprimirPocoAPoco(MAGENTA + "  * Daño a enemigo: " + dañovarita + ", Salud enemigo actual: " + SaludEnemigo + RESET, 10);
                                break;
                            case 4:
                                Salud[0] += 10;
                                imprimirPocoAPoco(MAGENTA + "  * Salud: +10" + RESET, 20);
                                if (Salud[0] > SaludMax[0]) {
                                    Salud[0] = SaludMax[0];
                                    imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 15);
                                    imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 15);
                                }
                                imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 20);
                                break;
                            case 5:
                                Salud[4] += 15;
                                imprimirPocoAPoco(MAGENTA + "  * Escudo mágico: +15" + RESET, 20);
                                break;
                        }


                    }


                    EnergiaVarita[0] = 0;

                    System.out.println("");

                    //-------------------------------------------------------------------------------------------------------------------
                } else {
                    imprimirPocoAPoco(ROJO + "  Energía insuficiente." + RESET, 15);
                }
                break;
            default:
                imprimirPocoAPoco(ROJO + "  ¡Intentalo de nuevo!" + RESET, 15);
                scanner.nextLine();
                BastonMago_Nivel_2();
                break;
        }

    }
    private static void BastonMago() {
        Scanner scanner = new Scanner(System.in);
       if (NivelBaston_1){
           BastonMago_Nivel_1();
       } else if (NivelBaston_2) {
           BastonMago_Nivel_2();
       }
       else if (NivelBaston_3) {
           BastonMago_Nivel_3();
       }

    }
    private static void VaritaFuego() {

        imprimirPocoAPoco(RESET + "  Descripción:", 10);
        imprimirPocoAPoco("  Una varita con la capacidad de hacer daño constante a un enemigo.", 7);
        //SaludEnemigo = SaludEnemigo - DañoVarita[1];
        EnergiaVarita[1] -= 1;
        FuegoVarita += DañoVarita[1];
        System.out.println("");

    }
    private static void VaritaHielo() {
        ContadorVHielo = 0;
        EnergiaVarita[2] -= 1;

        Agilidad[3] += 3; //Esta en la parte de verificar si el jugador puede dañar al enemigo, restandole esta agilidad al enemigo
        if (Agilidad[3] > 3) {
            Agilidad[3] = 3;
            imprimirPocoAPoco(ROJO + "  Este tipo de magia ya estaba activo, solo durará más tiempo." + RESET, 19);
        }
        imprimirPocoAPoco(RESET + "  Descripción:", 10);
        imprimirPocoAPoco("  Una varita con la capacidad de dificultar la capacidad de esquivar del enemigo.", 7);
        imprimirPocoAPoco(MAGENTA + "  * Agilidad del enemigo: -" + Agilidad[3] + RESET, 7);
        System.out.println("");
    }
    private static void VaritaDebilidad() {
        DañoVarita[3] += 2; //Daño de varita convertida en probabilidad de atacar que se le resta al enemigo
        ContadorVDebilidad = 0;
        EnergiaVarita[3] -= 1;

        if (DañoVarita[3] > 2) {
            DañoVarita[3] = 2;
            imprimirPocoAPoco(ROJO + "  Este tipo de magia ya estaba activo, solo durará más tiempo." + RESET, 19);
        }

        imprimirPocoAPoco(RESET + "  Descripción:", 10);
        imprimirPocoAPoco("  Una varita con la capacidad de debilitar al enemigo, reduciendo su fuerza y capacidad de atacar al jugador.", 7);
        imprimirPocoAPoco(MAGENTA + "  * Probabilidad de golpear al jugador: -" + DañoVarita[3] + RESET, 7);
        imprimirPocoAPoco(MAGENTA + "  * Fuerza de enemigo por varita: -3" + RESET, 7);
        System.out.println("");
    }
    private static void VaritaCuración() {
        Salud[0] += DañoVarita[4]; //El daño lo convierto en vida.
        EnergiaVarita[4] -= 1;
        imprimirPocoAPoco(RESET + "  Descripción:", 10);
        imprimirPocoAPoco("  Una varita con la capacidad de restaurar la salud perdida del jugador.", 7);
        imprimirPocoAPoco(MAGENTA + "  * Salud de jugador +" + DañoVarita[4], 7);
        if (Salud[0] > SaludMax[0]) {
            Salud[0] = SaludMax[0];
            imprimirPocoAPoco(CYAN + "  La magia de curación supera el límite de la salud máxima del jugador.", 10);
            imprimirPocoAPoco("  La salud aplicada sobrante se ve negada." + RESET, 10);
            System.out.println("");
        }
        imprimirPocoAPoco(MAGENTA + "  * Salud de jugador actual: " + Salud[0] + RESET, 7);
        System.out.println("");

    }
    private static void VaritaProtección() {
        ContadorVProtección = 0;
        Agilidad[2] = 15; //Esta en la verificación de si o no atacar al jugador
        EnergiaVarita[5] -= 1;

        imprimirPocoAPoco(RESET + "  Descripción:", 10);
        imprimirPocoAPoco("  Una varita con la capacidad de proteger al jugador de cualquier ataque este turno.", 7);
        imprimirPocoAPoco(MAGENTA + "  * Agilidad por varita +" + Agilidad[2] + RESET, 7);
        System.out.println("");
    }

    private static double calcularProbabilidadFallo(int indexMonstruo) {
        // La probabilidad de fallo aumenta con la fuerza del monstruo
        // Ejemplo: Monstruo más fuerte tiene mayor probabilidad de fallo
        double[] probabilidadFallo = {0.2, 0.2, 0.15, 0.25, 0.25, 0.24, 0.23, 0.3, 0.3, 0.4, 0.25, 0.10};
        return probabilidadFallo[indexMonstruo];
    }
    private static void mostrarMonstruos(ArrayList<Integer> monstruosInvocados) {
        if (!MonstruoEnCombate) {
            imprimirPocoAPoco(AMARILLO + "  Invocaciones disponibles:" + RESET, 10);
            for (int i = 0; i < monstruosInvocados.size(); i++) {
                int index = monstruosInvocados.get(i);
                imprimirPocoAPoco(VERDE + "  " + (i + 1) + ". " + NombreMonstruo[index] + RESET, 7);
            }
        }
    }
    private static void elegirMonstruo(ArrayList<Integer> monstruosInvocados) {
        Scanner scanner = new Scanner(System.in);
        if (!MonstruoEnCombate) {
            imprimirPocoAPoco(AZUL + "  Elige un monstruo para invocar: " + RESET, 10);

            if (scanner.hasNextInt()) {
                int eleccion = scanner.nextInt();
                scanner.nextLine();  // Consumir el salto de línea

                // Verificar que la elección esté dentro del rango disponibles
                if (eleccion < 1 || eleccion > monstruosInvocados.size()) {
                    System.out.println(ROJO + "  ¡Que mal!, opción no disponible, intenta de nuevo en el siguiente turno." + RESET);
                    System.out.println("");
                    return; // Salir del método si la elección no es válida
                }

                int indexMonstruo = monstruosInvocados.get(eleccion - 1);
                System.out.println("");

                // Calcular probabilidad de fallo
                double probabilidadFallo = calcularProbabilidadFallo(indexMonstruo);
                double aleatorio = Math.random();
                System.out.println("  Invocando");
                imprimirPocoAPoco("  . . .", 400);
                if (aleatorio < probabilidadFallo) {
                    imprimirPocoAPoco(ROJO + "  La invocación ha fallado." + RESET, 10);
                    System.out.println("");
                } else {
                    switch (indexMonstruo) {
                        case 0:
                            Fenix();
                            break;
                        case 1:
                            GolemDePiedra();
                            break;
                        case 2:
                            EspirituDelBosque();
                            break;
                        case 3:
                            DragonDeHielo();
                            break;
                        case 4:
                            GuardianDeHierro();
                            break;
                        case 5:
                            AngelGuardian();
                            break;
                        case 6:
                            ElfoSanador();
                            break;
                        case 7:
                            VampiroAncestral();
                            break;
                        case 8:
                            EspectroSombrio();
                            break;
                        case 9:
                            NigromanteOscuro();
                            break;
                        case 10:
                            SombraToxica();
                            break;
                        default:
                            imprimirPocoAPoco(ROJO + "  Opción no válida." + RESET, 10);
                    }
                    imprimirPocoAPoco("---------------------------------------------------", 3);
                    imprimirPocoAPoco(AMARILLO + "  Atributos de invocación:" + RESET, 10);
                    imprimirPocoAPoco("  * Nivel de invocación: " + NivelInvocado, 7);
                    imprimirPocoAPoco("  * Salud de invocación: " + SaludInvocado, 7);
                    imprimirPocoAPoco("  * Daño de invocación: " + DañoMinInvocacion + " - " + DañoMaxInvocacion, 7);
                    imprimirPocoAPoco("  * Agilidad de invocación: " + AgilidadInvocado, 7);
                    imprimirPocoAPoco("  * Curación de invocación: " + CuraciónInvocado, 7);
                    imprimirPocoAPoco("---------------------------------------------------", 3);
                    imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                    scanner.nextLine();
                    System.out.println("");
                }
            } else {
                scanner.nextLine();  // Consumir la entrada no numérica
                imprimirPocoAPoco(ROJO + "  Ups, vuelve a intentar con un número en el siguiente turno." + RESET, 10);

            }
        }
    }


    private static void Fenix() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Fénix." + RESET, 10);
        MonstruoEnCombate = true;

        NivelInvocado = NivelMonstruo[0];
        NombreInvocado = NombreMonstruo[0];
        SaludInvocado = SaludMonstruo[0];
        DañoMaxInvocacion = DañoMonstruo[0];
        DañoMinInvocacion = DañoMaxInvocacion - 10;
        AgilidadInvocado = AgilidadMoustro[0];
        CuraciónInvocado = CuraciónMonstruo[0];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(0) + 0.07;

    }
    private static void GolemDePiedra() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Gólem de Piedra." + RESET, 10);
        MonstruoEnCombate = true;
        NivelInvocado = NivelMonstruo[1];
        NombreInvocado = NombreMonstruo[1];
        SaludInvocado = SaludMonstruo[1];
        DañoMaxInvocacion = DañoMonstruo[1];
        DañoMinInvocacion = DañoMaxInvocacion - 5;
        AgilidadInvocado = AgilidadMoustro[1];
        CuraciónInvocado = CuraciónMonstruo[1];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(1) + 0.13;

    }
    private static void EspirituDelBosque() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Espíritu del Bosque." + RESET, 10);
        MonstruoEnCombate = true;
        NivelInvocado = NivelMonstruo[2];
        NombreInvocado = NombreMonstruo[2];
        SaludInvocado = SaludMonstruo[2];
        DañoMaxInvocacion = DañoMonstruo[2];
        DañoMinInvocacion = DañoMaxInvocacion - 3;
        AgilidadInvocado = AgilidadMoustro[2];
        CuraciónInvocado = CuraciónMonstruo[2];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(2);

    }

    private static void DragonDeHielo() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Dragón de Hielo." + RESET, 10);
        MonstruoEnCombate = true;
        NivelInvocado = NivelMonstruo[3];
        NombreInvocado = NombreMonstruo[3];
        SaludInvocado = SaludMonstruo[3];
        DañoMaxInvocacion = DañoMonstruo[3];
        DañoMinInvocacion = DañoMaxInvocacion - 10;
        AgilidadInvocado = AgilidadMoustro[3];
        CuraciónInvocado = CuraciónMonstruo[3];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(3);

    }
    private static void GuardianDeHierro() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Guardián de Hierro." + RESET, 10);
        MonstruoEnCombate = true;
        NivelInvocado = NivelMonstruo[4];
        NombreInvocado = NombreMonstruo[4];
        SaludInvocado = SaludMonstruo[4];
        DañoMaxInvocacion = DañoMonstruo[4];
        DañoMinInvocacion = DañoMaxInvocacion - 10;
        AgilidadInvocado = AgilidadMoustro[4];
        CuraciónInvocado = CuraciónMonstruo[4];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(4);

    }
    private static void AngelGuardian() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Ángel Guardián." + RESET, 10);
        MonstruoEnCombate = true;
        NivelInvocado = NivelMonstruo[5];
        NombreInvocado = NombreMonstruo[5];
        SaludInvocado = SaludMonstruo[5];
        DañoMaxInvocacion = DañoMonstruo[5];
        DañoMinInvocacion = DañoMaxInvocacion - 5;
        AgilidadInvocado = AgilidadMoustro[5];
        CuraciónInvocado = CuraciónMonstruo[5];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(5);

    }
    private static void ElfoSanador() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Elfo Sanador." + RESET, 10);
        MonstruoEnCombate = true;
        NivelInvocado = NivelMonstruo[6];
        NombreInvocado = NombreMonstruo[6];
        SaludInvocado = SaludMonstruo[6];
        DañoMaxInvocacion = DañoMonstruo[6];
        DañoMinInvocacion = DañoMaxInvocacion - 10;
        AgilidadInvocado = AgilidadMoustro[6];
        CuraciónInvocado = CuraciónMonstruo[6];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(6);

    }
    private static void VampiroAncestral() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Vampiro Ancestral." + RESET, 10);
        MonstruoEnCombate = true;
        InvocaciónVampi = true;
        NivelInvocado = NivelMonstruo[7];
        NombreInvocado = NombreMonstruo[7];
        SaludInvocado = SaludMonstruo[7];
        DañoMaxInvocacion = DañoMonstruo[7];
        DañoMinInvocacion = DañoMaxInvocacion - 5;
        AgilidadInvocado = AgilidadMoustro[7];
        CuraciónInvocado = CuraciónMonstruo[7];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(7);

    }
    private static void EspectroSombrio() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Espectro Sombrío." + RESET, 10);
        MonstruoEnCombate = true;
        NivelInvocado = NivelMonstruo[8];
        NombreInvocado = NombreMonstruo[8];
        SaludInvocado = SaludMonstruo[8];
        DañoMaxInvocacion = DañoMonstruo[8];
        DañoMinInvocacion = DañoMaxInvocacion - 5;
        AgilidadInvocado = AgilidadMoustro[8];
        CuraciónInvocado = CuraciónMonstruo[8];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(8);

    }
    private static void NigromanteOscuro() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado al Nigromante Oscuro." + RESET, 10);
        MonstruoEnCombate = true;
        InvocaciónNigro = true;
        NivelInvocado = NivelMonstruo[9];
        NombreInvocado = NombreMonstruo[9];
        SaludInvocado = SaludMonstruo[9];
        DañoMaxInvocacion = DañoMonstruo[9];
        DañoMinInvocacion = DañoMaxInvocacion - 7;
        AgilidadInvocado = AgilidadMoustro[9];
        CuraciónInvocado = CuraciónMonstruo[9];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(9);

    }
    private static void SombraToxica() {
        imprimirPocoAPoco(MAGENTA + "  Has invocado a Sombra Tóxica." + RESET, 10);
        MonstruoEnCombate = true;
        InvocaciónSombTox = true;
        NivelInvocado = NivelMonstruo[10];
        NombreInvocado = NombreMonstruo[10];
        SaludInvocado = SaludMonstruo[10];
        DañoMaxInvocacion = DañoMonstruo[10];
        DañoMinInvocacion = DañoMaxInvocacion - 10;
        AgilidadInvocado = AgilidadMoustro[10];
        CuraciónInvocado = CuraciónMonstruo[10];

        ProbabilidadCriticoInvocacion = calcularProbabilidadFallo(10);

    }




    // Sistema de combate------------------------------------------------------------------------------------------------------------------------------
    private static void SimularEncuentro() {
        Scanner scanner = new Scanner(System.in);
        EnCombateJugador = false;
        CombateInicio(); //Se coloco en la parte de enemigos para evitar usar objetos que dañen al enemigo antes del combate.
        if (ValleDuendeMusica) {
            //nada

        } else if (InfinitoModo) {
            //nada
        } else {
            if (NombreJugador.equals("revanchita")) {
                audioPlayer.stop();

                audioPlayer.play("muertesubita.wav");
                imprimirPocoAPoco(MAGENTA + "  ¡Modo REVANCHITA!" + RESET, 40);
                System.out.println("");
            } else {
                audioPlayer.stop();

                audioPlayer.play("batalla.wav");
            }
        }

        EnCombateJugador = true;
        while (SaludEnemigo > 0) {
            //Tal vez de esta manera ya no deja de generar error con 0 de valor, si se actualiza todo el tiempo.
            imprimirPocoAPoco("-------- Turno n°" + ContadorTurnos + " ------------------------------------", 7);
            ContadoresEfectoVarita();
            VerificacionVaritas();
            Contadores(); //Elegir varitas esta dentro de contadores *_*
            calcularDañoTotalJugador();
            ContadorRecargaVaritasCombate();

            imprimirPocoAPoco("  Turno de " + NombreJugador + ".", 20);

            CombateTurnoJugador(); //Turno 1 sin velocidad activa
            if (ContadorVelocidad[0] > 0) {
                imprimirPocoAPoco(MAGENTA + "  Atacas dos veces este turno por tu poción de velocidad.", 10);
                CombateTurnoJugador();//Turno 2 con velocidad activa
            }
            if (VelocidadEspada) {
                VelocidadEspada = false;
                if (Salud[3] > 0){
                    imprimirPocoAPoco(MAGENTA + "  Atacas una vez más este turno por encantamiento de velocidad de espada.", 10);
                    int dañoaenemigo = (int) (Math.random() * Daño[1]) + 1;


                    SaludEnemigo -=  dañoaenemigo; if (SaludEnemigo < 0) {SaludEnemigo = 0;}

                    imprimirPocoAPoco(VERDE + "  ->> Daño a enemigo: " + dañoaenemigo + BLANCO + " (Este ataque, realizara solo el daño de la espada.)" + RESET, 10);
                    imprimirPocoAPoco(VERDE + "  ->> Salud de enemigo actual: " + SaludEnemigo + RESET, 10);
                    System.out.println("");

                    if (Salud[3] > 0) {
                        Salud[3]--; // Reducir durabilidad de la espada
                        if (Salud[3] <= 0) {
                            Salud[3] = 0; // La espada se rompe
                            Daño[1] = 0; // El daño del arma se vuelve cero
                            imprimirPocoAPoco(ROJO + "  Tu espada se ha roto y ha sido eliminada del equipamiento." + RESET, 20);
                            System.out.println("");
                            EncantamientoEspadaCritico = false; CriticoEspada = false; EncantamientoEspadaCurativo = false; EncantamientoEspadaResistencia = false; EncantamientoEspadaVelocidad = false; VelocidadEspada = false;
                        }
                    }
                }
            }


            ContadoresAlteradoresEstadosNegativos();
            SaludArmaduraActual = Salud[1];//encantamiento
            SaludEscudoActual = Salud[2];//encantamiento


            if (SaludEnemigo <= 0) {
                break;//Salir del bucle
            }


            if (MonstruoEnCombate) {
                CombateInvocación();//Turno 1 de invocación
                if (ContadorVelocidad[1] > 0){
                    imprimirPocoAPoco(MAGENTA + "  La invocación ataca dos veces este turno por poción de velocidad.", 15);
                    CombateInvocación();//Turno 2 de invocación por poción de velocidad
                }
                if (SaludEnemigo <= 0) {
                    break;//Salir del bucle
                }

                CombateInvocaciónEnemigo();
               if (ContadorVelocidad[2] > 0){
                   imprimirPocoAPoco(MAGENTA + "  El enemigo ataca dos veces a invocación, por poción de velocidad.", 15);
                   CombateInvocaciónEnemigo();
               }
            }
            else {
                CombateTurnoEnemigo();
                if (ContadorVelocidad[2] > 0){
                    imprimirPocoAPoco(MAGENTA + "  El enemigo ataca dos veces este turno por poción de velocidad.", 15);
                    CombateTurnoEnemigo();
                }
            }
            System.out.println("");
            ContadoresEnemigo();
            DecicionEnemigo();


            if (Salud[0] <= 0) {
                imprimirPocoAPoco(ROJO + "  ¡Has muerto!" + RESET, 20);
                imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                scanner.nextLine(); // Espera a que el usuario presione Enter
                imprimirPocoAPoco("  El programa se cerrara en breve", 2);
                imprimirPocoAPoco("  . . . . . . .", 500);
                System.exit(0);
            }
            imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
            scanner.nextLine(); // Espera a que el usuario presione Enter

            varitasPoseidas.clear();

            if (ClaseInvocador) {
               ContadorParaInvocar++;
                if (ContadorParaInvocar >= 3) {
                    mostrarMonstruos(monstruosInvocados);
                    elegirMonstruo(monstruosInvocados);
                }
            }ContadoresInvocación();

        }
        //Termina while



        EnCombateJugador = false;
        System.out.println("");
        imprimirPocoAPoco(AZUL + "  ¡Has derrotado al enemigo!", 20);
        System.out.println("");
        imprimirPocoAPoco("  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
        System.out.println("  Limpiando...");
        imprimirPocoAPoco(" . . .", 300);
        borrarConsola(1000);

        System.out.println("");
        int expobtemida = 0;
        switch (NivelEnemigo) {
            case "Nivel 1":
                expobtemida += 5 + ( (int) ContadorTurnos * 0.2);
                Experiencia += expobtemida;
                break;
            case "Nivel 2":
                expobtemida += 7 + ( (int) ContadorTurnos * 0.3);
                Experiencia += expobtemida;
                break;
            case "Nivel 3":
                expobtemida += 10 + ( (int) ContadorTurnos * 0.6);
                Experiencia += expobtemida;
                break;
            default:

                break;

        }
        imprimirPocoAPoco( "Nivel de enemigo: " + NivelEnemigo + ", Turnos de partida: " + ContadorTurnos+",  Exp: +" + expobtemida +RESET, 5);
        System.out.println("");

        if (Experiencia >= ExperienciaMax && NivelJugador < 3){
            Experiencia -= ExperienciaMax;
            ExperienciaMax *= 1.7;
            NivelJugador += 1;

            PorcentajeReduccionDañoEspada *= 1.2;
            ProbabilidadCriticoJugador *= 1.30;

            SaludMax[0] *= 1.2;
            if (NivelJugador == 2) {
                Daño[0] += 2;
                Agilidad[0] += 1;
            }else   if (NivelJugador == 3){
                Daño[0] += 2;
                Agilidad[0] += 1;
            }


            imprimirPocoAPoco("  * ¡Felicidades, subes de nivel!", 5);
            System.out.println("");
            mostrarimagen("subirnivel.txt");
            System.out.println("");
            imprimirPocoAPoco( CYAN +"  -> Nivel de jugador: "+ NivelJugador + RESET, 5);
            imprimirPocoAPoco( CYAN +"  -> Salud máxima de jugador: "+ SaludMax[0] + RESET, 5);
            imprimirPocoAPoco( CYAN +"  -> Daño base máximo de jugador: "+ Daño[0] + RESET, 5);
            imprimirPocoAPoco( CYAN +"  -> Agilidad de jugador: "+ Agilidad[0] + RESET, 5);
            imprimirPocoAPoco( CYAN +"  -> Probabilidad critico de jugador: "+ (int)(100 * (ProbabilidadCriticoJugador) ) + "%" +  RESET, 5);
            imprimirPocoAPoco( CYAN +"  -> Daño de espada de jugador: "+ (int)(100 * (PorcentajeReduccionDañoEspada) ) + "%" +  RESET, 5);

        }
        else if (Experiencia >= ExperienciaMax  && NivelJugador == 3) {

                Experiencia -= ExperienciaMax;
                ExperienciaMax *= 1.25;
                Cantidades[18]++;
                imprimirPocoAPoco("  * ¡Felicidades, has alcanzado el nivel máximo!", 5);
                imprimirPocoAPoco("  Como recompensa por tu dedicación, has recibido un objeto exclusivo.", 5);
                System.out.println("");
                System.out.println("");
                mostrarimagen("pcuraciongrande.txt");
                System.out.println("");
                imprimirPocoAPoco(CYAN + "  Se te recompensa con: " + Objetos[18] + RESET, 10);


        }



        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
        System.out.println("  Limpiando...");
        imprimirPocoAPoco(" . . .", 200);
        borrarConsola(100);

        if (!InfinitoModo) {
            mostrarInventario();
            System.out.println("");
            imprimirPocoAPoco("  ¿Quieres usar algo de tu inventario?" + VERDE + " (1. si) (2. no)", 10);
            imprimirPocoAPoco(AZUL + "  Escoge una de las dos opciones anteriores." + RESET, 5);
            int opcionConsumible = scanner.nextInt();
            System.out.println("");
            if (opcionConsumible == 1) {
                UsarConsumibles();
            }
            imprimirPocoAPoco("  ¡Buena suerte!", 20);
            imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 5);
            scanner.nextLine();
        }

        AnulacionEstados();

        imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
        scanner.nextLine();
        System.out.println("  Cargando...");
        imprimirPocoAPoco(" . . .", 300);
        borrarConsola(1000);
        System.out.println("");
    }
    private static void CombateInicio() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        imprimirPocoAPoco(ROJO + "  ¡Te has encontrado con un enemigo!" + RESET, 15);
        System.out.println("");
            System.out.println("");
            imprimirPocoAPoco("  ¿Quieres usar algo de tu inventario?", 15);
            imprimirPocoAPoco(VERDE + "  1. Si.", 4);
            imprimirPocoAPoco("  2. No" + RESET, 4);
            imprimirPocoAPoco(AMARILLO + "  3. Ver todas las estadísticas e inventario. (No podrás usar nada)" + RESET, 4);
            imprimirPocoAPoco(AZUL + "  Escoge una de las tres opciones anteriores." + RESET, 5);
            int opcionConsumible = scanner.nextInt();
            System.out.println("");

            if (opcionConsumible == 1) {
                UsarConsumibles();
            } else if (opcionConsumible == 2) {
                imprimirPocoAPoco("  ¡Buena suerte!", 15);
                System.out.println("");
            } else if (opcionConsumible == 3) {
                mostrarInventario();
                imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                scanner.nextLine(); // Consume newline left-over
                scanner.nextLine(); // Espera a que el usuario presione Enter
            } else {
                imprimirPocoAPoco("  ¡Buena suerte!  (*u*)", 10);
                imprimirPocoAPoco(AZUL + "  Presiona Enter para continuar..." + RESET, 10);
                scanner.nextLine(); // Consume newline left-over
                scanner.nextLine(); // Espera a que el usuario presione Enter
            }

                System.out.println("  Cargando...");
                imprimirPocoAPoco(" . . .", 300);
                borrarConsola(1000);




        switch (EnemigoNivelSelec) {
            case 1, 2, 3, 4:
                AsignarAtributosEnemigo(EnemigoGrupoSelec, EnemigoNivelSelec, EnemigoIndiceSelec, EnemigoCategoriaSelec);
                break;
            case 0:
                EnemigoEspecificoPerso();
                break;
            default:
                System.out.println("Advertencia: Nivel no reconocido. Llamando a EnemigoEspecificoPerso().");
                imprimirPocoAPoco(" . . . ", 300);
                EnemigoEspecificoPerso();
                break;
        }
        borrarConsola(100);

    }
    private static void AnulacionEstados(){
        NivelEnemigo = "";
        varitasPoseidas.clear();
        ContadorTurnos = 1;

        if (Salud[4] > 0){
            Salud[4] = 0;
            imprimirPocoAPoco(BLANCO + "  El escudo mágico es cancelada automaticamente." + RESET, 10);
        }

        ContadorVeneno[1] = 0;
        if (MonstruoEnCombate == true) {
            MonstruoEnCombate = false;
            InvocaciónNigro = false;
            InvocaciónVampi = false;
            InvocaciónSombTox = false;

            ContadorAgilidad[1] = 0;
            Agilidad[6] = 0;
            ContadorFuerza[1] = 0;
            Daño[3] = 0;
            ContadorResistencia[1] = 0;
            ContadorVelocidad[1] = 0;
            ContadorDestreza[1] = 0;
            ContadorVeneno[1] = 0;

            imprimirPocoAPoco(BLANCO + "  La invocación se a retirado automaticamente." + RESET, 10);
        }

        ContadorOportuno = 0;
        if (
                    Agilidad[1] > 0 ||
                    Agilidad[4] > 0 ||
                    Daño[2] > 0 ||
                    ContadorResistencia[0] > 0 ||
                    ContadorVelocidad[0] > 0 ||
                    ContadorConcentración > 0 ||
                    ContadorDestreza[0] > 0 ||
                    AgilidadBaston > 0 ||
                    FuerzaBaston > 0 ||
                    Agilidad[2] > 0
        ) {

            ContadorAgilidad[0] = 0;
            Agilidad[1] = 0;//pocion

            ContadorAgilidad[3] = 0;
            Agilidad[4] = 0;//antorcha

            Daño[2] = 0;//pocion
            ContadorFuerza[0] = 0;

            ContadorResistencia[0] = 0;
            ContadorVelocidad[0] = 0;
            ContadorConcentración = 0;
            ContadorDestreza[0] = 0;



            AgilidadBaston = 0;ContadorAgilidadBaston = 0;
            FuerzaBaston = 0;ContadorFuerzaBaston = 0;
            Agilidad[2] = 0;ContadorVProtección = 0;


            imprimirPocoAPoco(BLANCO + "  Tus potenciadores de estado se han cancelado automaticamente" + RESET, 10);
        }



        if (ContadorVeneno[0] > 0 ||
                FuegoVarita > 0 ||
                Agilidad[3] > 0 ||
                DañoVarita[3] > 0 ||
                AgilidadEnemigoPocion > 0 ||
                DañoEnemigoPocion > 0 ||
                ContadorResistencia[2] > 0 ||
                ContadorVelocidad[2] > 0 ||
                ContadorVeneno[2] > 0

        ){
            ContadorVeneno[0] = 0;
            FuegoVarita = 0;
            Agilidad[3] = 0; ContadorVHielo = 0;// varita de hielo
            DañoVarita[3] = 0; ContadorVDebilidad = 0; //El daño se convierte en - probabilidad golpear a jugador.


            ContadorFuerza[2] = 0;
            ContadorAgilidad[2] = 0;
            AgilidadEnemigoPocion = 0;
            DañoEnemigoPocion = 0;
            ContadorResistencia[2] = 0;
            ContadorVelocidad[2] = 0;
            ContadorVeneno[2] = 0;

            imprimirPocoAPoco(BLANCO + "  Las alteraciones de estado del enemigo se han cancelado automaticamente, por que se petateo." + RESET, 10);
            imprimirPocoAPoco(BLANCO + "                                                                                                  *_*" + RESET, 1);
        }



    }

    private static void CombateInvocación() {
        Scanner scanner = new Scanner(System.in);
        int ProbabididadGolepearEnemigo = (int) (Math.random() * 10) + 1;
        imprimirPocoAPoco("  Turno de " + NombreInvocado + ".", 15);
        if (Agilidad[3] > 0){
            imprimirPocoAPoco(VERDE + "  Probabilidad de acierto de invocación: " + ProbabididadGolepearEnemigo + ", Efecto de hielo de enemigo: +" + Agilidad[3] + ", Probabilidad de acierto final de invocación: " + (ProbabididadGolepearEnemigo + Agilidad[3]), 15); //El daño de varita debilidad, convertida en - probabilidad de atacar.

        }
        else {
            imprimirPocoAPoco(VERDE + "  Probabilidad de acierto de invocación: " + ProbabididadGolepearEnemigo + RESET, 15);

        }

        if ((ProbabididadGolepearEnemigo + Agilidad[3]) >= (AgilidadEnemigo + AgilidadEnemigoPocion)) {//Varita de hielo en acción
            calcularDañoInvocacion();
            imprimirPocoAPoco(VERDE + "  * Daño a enemigo por invocación: " + DañoInvocado, 15);
            if (ContadorResistencia[2] > 0){
                DañoInvocado *= 0.8;
                imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia:" + DañoInvocado, 15);
            }
            SaludEnemigo -= DañoInvocado;

            if (SaludEnemigo < 0) {
                SaludEnemigo = 0;
            }
            imprimirPocoAPoco(VERDE + "  * Salud de enemigo actual: " + SaludEnemigo + RESET, 15);
            if (InvocaciónNigro) {
                DañoMaxInvocacion += 3;
                imprimirPocoAPoco(MAGENTA + "  -> Daño de invocación +3, daño actual: " + DañoMaxInvocacion + RESET, 15);
            }
            if (InvocaciónVampi) {
                DañoInvocado *= 0.5;
                SaludInvocado += DañoInvocado;
                imprimirPocoAPoco(MAGENTA + "  * Salud de invocación actual: " + SaludInvocado + RESET, 15);
            }
            if (InvocaciónSombTox){
                ContadorVeneno[0] += 3;
                imprimirPocoAPoco(MAGENTA + "  -> Valor de veneno aplicado a enemigo: + 3" +RESET, 15);
            }
            System.out.println("");

        }
        else if ((ProbabididadGolepearEnemigo + Agilidad[3]) < (AgilidadEnemigo + AgilidadEnemigoPocion)) {
            imprimirPocoAPoco(VERDE + "  - Ataque de invocación falló." + RESET, 15);
            if (InvocaciónNigro) {
                DañoMaxInvocacion += 3;
                imprimirPocoAPoco(MAGENTA + "  -> Daño de invocación +3, daño actual: " + DañoMaxInvocacion + RESET, 15);
            }

        }

        if (CuraciónInvocado > 0) {

            if (Salud[0] < SaludMax[0]) {
                boolean resultado = Math.random() < 0.69;
                if (resultado) {
                    Salud[0] += CuraciónInvocado;
                    imprimirPocoAPoco(MAGENTA + "  * La invocación le proporciona +" + CuraciónInvocado + " salud al jugador." + RESET, 15);
                    if (Salud[0] > SaludMax[0]) {
                        Salud[0] = SaludMax[0];
                        imprimirPocoAPoco(CYAN + "  *! La curación aplicada por la invocación supera el límite de la salud máxima del jugador.", 15);
                        imprimirPocoAPoco("  ¡*! La salud aplicada sobrante se ve negada." + RESET, 15);

                    }
                }
            }
            else if (Salud[0] == SaludMax[0]) {
                boolean resultado = Math.random() < 0.40;
                if (resultado) {
                    SaludInvocado += 3;
                    imprimirPocoAPoco(CYAN + "  -> El jugador posee la salud máxima.", 15);
                    imprimirPocoAPoco("  * La invocación se cura a si misma + 3 de salud", 15);
                    if (SaludInvocado > 100){SaludInvocado = 100;  imprimirPocoAPoco(AMARILLO + "  *! La curación tiene un limite. " + CYAN, 15);}
                    imprimirPocoAPoco("  * Salud de la invocación actual: " + SaludInvocado + RESET, 15);
                }
            }
            System.out.println("");
        }


    }
    private static void CombateInvocaciónEnemigo() {
        System.out.println("");
        imprimirPocoAPoco("  Turno del enemigo " + EnemigoActualNombre + ".", 15);
        int ProbabididadGolepearInvocacion = (int) (Math.random() * 10) + 1;

        if (DañoVarita[3] > 0){
            imprimirPocoAPoco(AMARILLO + "  Probabilidad de acierto a invocación: " + ProbabididadGolepearInvocacion + ", Efecto de debilidad: -" + DañoVarita[3] + ", Probabilidad de acierto final a invocación: " + (ProbabididadGolepearInvocacion - DañoVarita[3]), 5); //El daño de varita debilidad, convertida en - probabilidad de atacar.

        }else {
            imprimirPocoAPoco(AMARILLO + "  Probabilidad de acierto a invocación: " + ProbabididadGolepearInvocacion, 15);

        }

        if ((ProbabididadGolepearInvocacion - DañoVarita[3]) >= (AgilidadInvocado + Agilidad[6])) {//Verifica si se puede golpear o fallar......

            calcularDañoEnemigo();
            DañoEnemigo = dañoBase;


            if (ParalisisPorEscudo){
                imprimirPocoAPoco(AMARILLO + "  ->> El enemigo esta paralizado.", 15);
                System.out.println("");
                ParalisisPorEscudo = false;
            }
            else if (DañoMagicoEnemigoSN){
                imprimirPocoAPoco(AMARILLO + "  * Daño mágico a invocación: " + DañoEnemigo, 15);
                if (ContadorResistencia[1] > 0) {
                    DañoEnemigo *= 0.5; // Reduce el daño por una poción un 50%
                    imprimirPocoAPoco(CYAN + "  {*} Daño mágico reducido por poción de resistencia: " + DañoEnemigo, 15);
                }
                SaludInvocado -= DañoEnemigo;
                imprimirPocoAPoco("  * Salud de invocación actual: " + SaludInvocado + RESET, 15);
                System.out.println("");

                if (SaludInvocado <= 0) {
                    SaludInvocado = 0;
                    MonstruoEnCombate = false;
                    InvocaciónNigro = false;
                    InvocaciónVampi = false;
                    InvocaciónSombTox = false;

                    ContadorAgilidad[1] = 0;
                    Agilidad[6] = 0;
                    ContadorFuerza[1] = 0;
                    Daño[3] = 0;
                    ContadorResistencia[1] = 0;
                    ContadorVelocidad[1] = 0;
                    ContadorDestreza[1] = 0;
                    ContadorVeneno[1] = 0;

                    ContadorParaInvocar = 0;
                    imprimirPocoAPoco(ROJO + "  " + NombreInvocado + " no puede continuar." + RESET, 15);
                    System.out.println("");

                }
            }
            else if (DañoEnemigo <= SaludInvocado){
                imprimirPocoAPoco(AMARILLO + "  * Daño a invocación: " + DañoEnemigo, 15);
                if (ContadorResistencia[1] > 0) {
                    DañoEnemigo *= 0.8; // Reduce el daño por una poción un 20%
                    imprimirPocoAPoco(CYAN + "  {*} Daño aplicado por poción de resistencia: " + DañoEnemigo, 15);
                }
                SaludInvocado -= DañoEnemigo;
                imprimirPocoAPoco("  * Salud de invocación actual: " + SaludInvocado + RESET, 15);
                System.out.println("");
                VerificarEnemigoHabilidad();//--------------------------------------------------------------------------------------------
                if (SaludInvocado <= 0) {
                    SaludInvocado = 0;
                    MonstruoEnCombate = false;
                    InvocaciónNigro = false;
                    InvocaciónVampi = false;
                    InvocaciónSombTox = false;

                    ContadorAgilidad[1] = 0;
                    Agilidad[6] = 0;
                    ContadorFuerza[1] = 0;
                    Daño[3] = 0;
                    ContadorResistencia[1] = 0;
                    ContadorVelocidad[1] = 0;
                    ContadorDestreza[1] = 0;
                    ContadorVeneno[1] = 0;

                    ContadorParaInvocar = 0;
                    imprimirPocoAPoco(ROJO + "  " + NombreInvocado + " no puede continuar." + RESET, 15);
                    System.out.println("");

                }


                //_-----------------------------------------------------------------------------------------------------
            }
            else if (DañoEnemigo > SaludInvocado){
                DañoRestanteInvocaciónCaida = DañoEnemigo - SaludInvocado;
                SaludInvocado = 0;
                MonstruoEnCombate = false;
                InvocaciónNigro = false;
                InvocaciónVampi = false;
                InvocaciónSombTox = false;

                ContadorAgilidad[1] = 0;
                Agilidad[6] = 0;
                ContadorFuerza[1] = 0;
                Daño[3] = 0;
                ContadorResistencia[1] = 0;
                ContadorVelocidad[1] = 0;
                ContadorDestreza[1] = 0;
                ContadorVeneno[1] = 0;

                ContadorParaInvocar = 0;
                imprimirPocoAPoco(AMARILLO + "  * Daño a invocación: " + DañoEnemigo, 15);
                imprimirPocoAPoco("  * Salud de invocación actual: " + SaludInvocado + RESET, 15);
                imprimirPocoAPoco(ROJO + "  " + NombreInvocado + " no puede continuar." + RESET, 15);
                System.out.println("");


                CombateTurnoEnemigoInvocaciónDañoJugador();

            }




        }
        else if ((ProbabididadGolepearInvocacion - DañoVarita[3]) < AgilidadInvocado) { //Agilidad del moustro invcocado
            if (ParalisisPorEscudo){
                imprimirPocoAPoco(AMARILLO + "  ->> El enemigo esta paralizado.", 15);
                System.out.println("");
                ParalisisPorEscudo = false;
            }
            else {
                if (DañoMagicoEnemigoSN){
                    imprimirPocoAPoco(AMARILLO + "  * Daño mágico a invocación: " + DañoEnemigo, 15);
                    if (ContadorResistencia[1] > 0) {
                        DañoEnemigo *= 0.5; // Reduce el daño por una poción un 50%
                        imprimirPocoAPoco(CYAN + "  {*} Daño mágico reducido por poción de resistencia: " + DañoEnemigo, 15);
                    }
                    SaludInvocado -= DañoEnemigo;
                    imprimirPocoAPoco("  * Salud de invocación actual: " + SaludInvocado + RESET, 15);
                    System.out.println("");

                    if (SaludInvocado <= 0) {
                        SaludInvocado = 0;
                        MonstruoEnCombate = false;
                        InvocaciónNigro = false;
                        InvocaciónVampi = false;
                        InvocaciónSombTox = false;

                        ContadorAgilidad[1] = 0;
                        Agilidad[6] = 0;
                        ContadorFuerza[1] = 0;
                        Daño[3] = 0;
                        ContadorResistencia[1] = 0;
                        ContadorVelocidad[1] = 0;
                        ContadorDestreza[1] = 0;
                        ContadorVeneno[1] = 0;

                        ContadorParaInvocar = 0;
                        imprimirPocoAPoco(ROJO + "  " + NombreInvocado + " no puede continuar." + RESET, 15);
                        System.out.println("");

                    }
                }
                else {
                    imprimirPocoAPoco(AMARILLO + "  - El enemigo falló.", 15);
                    System.out.println("");
                }
            }
        }


    }
    private static void CombateTurnoEnemigoInvocaciónDañoJugador() {
        int ProbabididadGolepearJugador = (int) (Math.random() * 5) + 1;

        if (DañoVarita[3] > 0) {
            imprimirPocoAPoco(AMARILLO + "  Probabilidad de acierto, máx 5: " + ProbabididadGolepearJugador + ", Efecto de debilidad: -" + DañoVarita[3] + ", Probabilidad de acierto final: " + (ProbabididadGolepearJugador - DañoVarita[3]), 15);
        } else {
            imprimirPocoAPoco(AMARILLO + "  Probabilidad de acierto, máx 5: " + ProbabididadGolepearJugador, 15);
        }

        if ((ProbabididadGolepearJugador - DañoVarita[3]) >= (Agilidad[0] + Agilidad[1] + Agilidad[2] + AgilidadBaston + Agilidad[4])) {

            if (Salud[4] > 0) { // Escudo mágico
                DañoEnemigo = DañoRestanteInvocaciónCaida;
                DañoRestanteInvocaciónCaida = 0;
                procesarEscudoMagico();
            }else if (Salud[2] > 0) { // Escudo físico
                DañoRestanteEscudoMagico = DañoRestanteInvocaciónCaida;
                DañoRestanteInvocaciónCaida = 0;
                procesarEscudoFisico();
            } else if (Salud[1] > 0) { // Armadura
                DañoRestanteEscudoFisico = DañoRestanteInvocaciónCaida;
                DañoRestanteInvocaciónCaida = 0;
                procesarArmadura();
            } else {
                DañoRestanteArmadura = DañoRestanteInvocaciónCaida;
                DañoRestanteInvocaciónCaida = 0;
                procesarJugador();
            }



        } else {
            imprimirPocoAPoco(AMARILLO + "  - El enemigo falló al intentar atacar al jugador, despues de atacar a la invocación.", 15);
            System.out.println("");
        }
    }

    private static void CombateTurnoJugador() {
        if (Salud[3] == 0 && Daño[1] > 0) {
            Daño[1] = 0; // El daño del arma se vuelve cero
            imprimirPocoAPoco(BLANCO + "  Error de espada corregido." + RESET, 15);
        }

        int ProbabididadGolepearEnemigo = (int) (Math.random() * 10) + 1;
        if (Agilidad[3] > 0){
            imprimirPocoAPoco(VERDE + "  Probabilidad de acierto: " + ProbabididadGolepearEnemigo + ", Efecto de hielo de enemigo: +" + Agilidad[3] + ", Probabilidad de acierto final: " + (ProbabididadGolepearEnemigo + Agilidad[3]), 15); //El daño de varita debilidad, convertida en - probabilidad de atacar.

        }else {
            imprimirPocoAPoco(VERDE + "  Probabilidad de acierto: " + ProbabididadGolepearEnemigo, 15);

        }
        if ((ProbabididadGolepearEnemigo + Agilidad[3]) >= (AgilidadEnemigo + AgilidadEnemigoPocion)) {//Varita de hielo en acción

            EncantamientoEspadaCritico_Activo();//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            calcularDañoJugador();
            CriticoEspada = false;


            imprimirPocoAPoco(VERDE + "  * Daño a enemigo: " + DañoTotal, 15);
            if (ContadorResistencia[2] > 0){
                DañoTotal *= 0.8;
                imprimirPocoAPoco(AMARILLO + "  {*!} Daño reducido, por poción de resistencia: " + DañoTotal, 15);
            }

            SaludEnemigo -= DañoTotal;
            if (SaludEnemigo < 0) {
                SaludEnemigo = 0;
            }
            imprimirPocoAPoco(VERDE + "  * Salud de enemigo actual: " + SaludEnemigo + RESET, 15);

            EncantamientoEspadaCurativo_Activo();//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            EncantamientoEspadaResistencia_Activo();
            EncantamientoEspadaVelocidad_Activo();

            System.out.println("");

            if (Salud[3] > 0) {
                Salud[3]--; // Reducir durabilidad de la espada
                if (Salud[3] <= 0) {
                    Salud[3] = 0; // La espada se rompe
                    Daño[1] = 0; // El daño del arma se vuelve cero
                    imprimirPocoAPoco(ROJO + "  Tu espada se ha roto y ha sido eliminada del equipamiento." + RESET, 15);
                    System.out.println("");
                    EncantamientoEspadaCritico = false; CriticoEspada = false; EncantamientoEspadaCurativo = false; EncantamientoEspadaResistencia = false; EncantamientoEspadaVelocidad = false; VelocidadEspada = false;
                }
            }
        } else if ((ProbabididadGolepearEnemigo + Agilidad[3]) < (AgilidadEnemigo + AgilidadEnemigoPocion)) {
            imprimirPocoAPoco(VERDE + "  - Tu ataque falló." + RESET, 15);
            System.out.println("");
        }
    }
    private static void CombateTurnoEnemigo() {
       imprimirPocoAPoco("  Turno del enemigo " + EnemigoActualNombre + ".", 15);
       int ProbabididadGolepearJugador = (int) (Math.random() * 10) + 1;

       if (DañoVarita[3] > 0) {
           imprimirPocoAPoco(AMARILLO + "  Probabilidad de acierto: " + ProbabididadGolepearJugador + ", Efecto de debilidad: -" + DañoVarita[3] + ", Probabilidad de acierto final: " + (ProbabididadGolepearJugador - DañoVarita[3]), 15);
       } else {
           imprimirPocoAPoco(AMARILLO + "  Probabilidad de acierto: " + ProbabididadGolepearJugador, 15);
       }

       if ((ProbabididadGolepearJugador - DañoVarita[3]) >= (Agilidad[0] + Agilidad[1] + Agilidad[2] + AgilidadBaston + Agilidad[4] + Agilidad[5])) {
           calcularDañoEnemigo();
           DañoEnemigo = dañoBase;

           if (ParalisisPorEscudo){
               imprimirPocoAPoco(AMARILLO + "  ->> El enemigo esta paralizado.", 15);
               System.out.println("");
               ParalisisPorEscudo = false;
           }
           else {
               if (DañoMagicoEnemigoSN){
                   if (Salud[4] > 0) {
                       procesarEscudoMagico();
                   } else {
                       procesarJugador();
                   }
               }
               else {
                   if (Salud[4] > 0) { // Escudo mágico
                       procesarEscudoMagico();
                   } else if (Salud[2] > 0) { // Escudo físico
                       procesarEscudoFisico();
                   } else if (Salud[1] > 0) { // Armadura
                       procesarArmadura();
                   } else {
                       procesarJugador();
                   }
                   VerificarEnemigoHabilidad();//--------------------------------------------------------------------------------------------
               }
           }

       }
       else {
           if (ParalisisPorEscudo){
               imprimirPocoAPoco(AMARILLO + "  ->> El enemigo esta paralizado.", 15);
               System.out.println("");
               ParalisisPorEscudo = false;
           }else {
               if (DañoMagicoEnemigoSN){
                   if (Salud[4] > 0) {
                       procesarEscudoMagico();
                   } else {
                       procesarJugador();
                   }
               }else{
               imprimirPocoAPoco(AMARILLO + "  - El enemigo falló.", 15);
               System.out.println("");
               }
           }
       }

   }

    private static void procesarEscudoMagico() {
        DañoRestanteEscudoMagico = 0;
        if (DañoMagicoEnemigoSN){

            if (DañoMagicoEnemigo <= Salud[4]) {
                Salud[4] -= DañoMagicoEnemigo;
                imprimirPocoAPoco(CYAN + "  - Tu escudo mágico bloquea el ataque mágico.", 15);
                imprimirPocoAPoco(MAGENTA +"  * Daño a escudo mágico: " + DañoMagicoEnemigo, 15);
                imprimirPocoAPoco(CYAN + "  * Durabilidad del escudo mágico actual: " + Salud[4] + AMARILLO, 15);
                if (Salud[4] <= 0){
                    Salud[4] = 0;
                    imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                }
                DañoMagicoEnemigoSN = false;
            }
            else {
                DañoRestanteEscudoMagico = DañoMagicoEnemigo - Salud[4];
                Salud[4] = 0;
                imprimirPocoAPoco(CYAN + "  - Tu escudo mágico bloquea parte del daño del ataque mágico.", 15);
                imprimirPocoAPoco(MAGENTA +"  * Daño a escudo mágico: " + DañoMagicoEnemigo, 15);
                imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                imprimirPocoAPoco(AMARILLO +"  * Daño mágico restante: " + DañoRestanteEscudoMagico + RESET, 15);


                    DañoRestanteArmadura = DañoRestanteEscudoMagico; //Lo tomo, para no tener que crear mas variables
                    DañoRestanteEscudoMagico = 0;
                    procesarJugador();

            }
        }
        else {
            if (DañoEnemigo <= Salud[4]) {
                Salud[4] -= DañoEnemigo;
                imprimirPocoAPoco(CYAN + "  - Tu escudo mágico bloquea el daño.", 15);
                imprimirPocoAPoco("  * Daño a escudo mágico: " + DañoEnemigo, 15);
                imprimirPocoAPoco("  * Durabilidad del escudo mágico actual: " + Salud[4] + AMARILLO, 15);
                if (Salud[4] <= 0){
                    Salud[4] = 0;
                    imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                }
            } else {
                DañoRestanteEscudoMagico = DañoEnemigo - Salud[4];
                Salud[4] = 0;
                imprimirPocoAPoco(CYAN + "  - Tu escudo mágico bloquea parte del daño.", 15);
                imprimirPocoAPoco("  * Daño a escudo mágico: " + DañoEnemigo, 15);
                imprimirPocoAPoco(ROJO + "  - Tu escudo mágico no resiste más y se desvanece.", 15);
                imprimirPocoAPoco(AMARILLO +"  * Daño restante: " + DañoRestanteEscudoMagico + RESET, 15);

                if (Salud[2] > 0) { // Escudo físico
                    procesarEscudoFisico();
                } else if (Salud[1] > 0) { // Armadura
                    DañoRestanteEscudoFisico = DañoRestanteEscudoMagico;
                    DañoRestanteEscudoMagico = 0;
                    procesarArmadura();
                } else {
                    DañoRestanteArmadura = DañoRestanteEscudoMagico;
                    DañoRestanteEscudoMagico = 0;
                    procesarJugador();
                }
            }
        }


    }
    private static void procesarEscudoFisico() {
        DañoRestanteEscudoFisico = 0 ;
        EncantamientoEscudoBloqueo_Activo();
        if (BloqueoEncantamiento){
            imprimirPocoAPoco(CYAN + "  ->> El enemigo fallo por encantamiento de bloqueo.", 15);
            BloqueoEncantamiento = false;
        }
        else {
            imprimirPocoAPoco(CYAN + "  - Tu escudo te protege.", 15);
            int DañoEscudo = 0;
            SaludEscudoActual = Salud[2];
            int DañoEscodoAntesDeReducido_1 = 0;
            if (DañoRestanteEscudoMagico > 0) {
                DañoEscodoAntesDeReducido_1 = DañoRestanteEscudoMagico;
                DañoEscudo = (int) (DañoRestanteEscudoMagico * 0.5);
                DañoRestanteEscudoMagico = 0;
            } else {
                DañoEscodoAntesDeReducido_1 = DañoEnemigo;
                DañoEscudo = (int) (DañoEnemigo * 0.5);
            }

            EncantamientoEscudoResistencia_Activo();
            int DañoEscodoAntesDeReducido_2 = 0;



            if (DañoEscudo <= Salud[2]) {

                imprimirPocoAPoco("  * El escudo bloquea parte del daño recibido -50%", 15);
                imprimirPocoAPoco("  * Daño infligido al escudo: " + DañoEscodoAntesDeReducido_1 , 15);
                imprimirPocoAPoco("  * Valor de daño reducido a aplicar: " + DañoEscudo, 15);


                if (ResistenciaEscudo){
                    DañoEscodoAntesDeReducido_2 = DañoEscudo;
                    DañoEscudo = (int) (DañoEscudo * 0.7);
                    imprimirPocoAPoco("  ¡El encantamiento de resistencia ha bloqueado parte del daño -30%!", 15);
                    imprimirPocoAPoco("  -*> Daño infligido al escudo: " + DañoEscodoAntesDeReducido_2 , 15);
                    imprimirPocoAPoco("  ->> Daño aplicado tras la resistencia: " + DañoEscudo, 15);
                    ResistenciaEscudo = false;
                }else {

                    imprimirPocoAPoco("  * Daño aplicado al escudo: " + DañoEscudo, 15);
                }
                Salud[2] -= DañoEscudo;
                imprimirPocoAPoco("  * La durabilidad del escudo actual es: " + Salud[2] + AMARILLO, 15);

                EncantamientoEscudoEspinas_Activo();
                EncantamientoEscudoParalisis_Activo();

                if (Salud[2] <= 0) {
                    Salud[2] = 0;
                    imprimirPocoAPoco(ROJO + "  Tu escudo se ha roto y ha sido eliminado de tu equipamiento." + CYAN, 15);
                    EncantamientoEscudoEspinas = false; EncantamientoEscudoBloqueo = false; BloqueoEncantamiento = false; EncantamientoEscudoResistencia = false; ResistenciaEscudo = false; EncantamientoEscudoParalisis = false;
                }
            } else {

                DañoRestanteEscudoFisico = DañoEscudo - Salud[2];
                //DañoRestanteEscudoFisico *= 2;
                Salud[2] = 0;
                imprimirPocoAPoco(CYAN + "  - Tu escudo te protege.", 15);

                EncantamientoEscudoEspinas_Activo();
                EncantamientoEscudoParalisis_Activo();
                imprimirPocoAPoco("  * Daño aplicado al escudo: " + DañoEscudo, 15);
                imprimirPocoAPoco(ROJO + "  Tu escudo se ha roto y ha sido eliminado de tu equipamiento." + CYAN, 15);
                imprimirPocoAPoco(AMARILLO + "  * Daño restante: " + DañoRestanteEscudoFisico, 15);
                EncantamientoEscudoEspinas = false; EncantamientoEscudoBloqueo = false; BloqueoEncantamiento = false; EncantamientoEscudoResistencia = false; ResistenciaEscudo = false; EncantamientoEscudoParalisis = false;
                if (Salud[1] > 0) { // Armadura
                    procesarArmadura();
                } else {
                    DañoRestanteArmadura = DañoRestanteEscudoFisico;
                    DañoRestanteEscudoFisico = 0 ;
                    procesarJugador();
                }
            }
        }
    }
    private static void procesarArmadura() {
       DañoArmadura = 0;
        SaludArmaduraActual = Salud[1];
        int DañoAntesReducciónGuerrero = 0;


        if (DañoRestanteEscudoFisico > 0){
            if (ClaseGuerrero) {
                DañoAntesReducciónGuerrero = DañoRestanteEscudoFisico;
                DañoRestanteEscudoFisico *= ReduccionGuerreroDañoObtenido;
            }
            DañoArmadura = DañoRestanteEscudoFisico;
            DañoRestanteEscudoFisico = 0;
        }
        else {
            if (ClaseGuerrero) {
                DañoAntesReducciónGuerrero = DañoEnemigo;

                DañoEnemigo *= ReduccionGuerreroDañoObtenido;
            }
            DañoArmadura = DañoEnemigo;
        }

        if (DañoArmadura <= Salud[1]) {
            imprimirPocoAPoco(CYAN + "  - La armadura recibió el daño en tu lugar.", 15);
            if (ClaseGuerrero){
                imprimirPocoAPoco("  * Daño a armadura: " + DañoAntesReducciónGuerrero, 15);
                imprimirPocoAPoco("  {**} Daño aplicado por resistencia de guerrero: " + DañoArmadura, 15);
            }else {
                imprimirPocoAPoco("  * Daño a armadura: " + DañoArmadura, 15);
            }

            Salud[1] -= DañoArmadura;
            imprimirPocoAPoco("  * Durabilidad de la armadura actual: " + Salud[1] + RESET, 15);

            EncantamientoArmaduraEspinas_Activo();
            EncantamientoArmaduraReparación_Activo();
            EncantamientoArmaduraAgilidad_Activo();
            EncantamientoArmaduraRecarga_Activo();


            if (Salud[1] <= 0) {
                Salud[1] = 0;
                imprimirPocoAPoco(ROJO + "  Tu armadura se ha roto y ha sido eliminada de tu equipamiento." + AMARILLO, 15);
               EncantamientoArmaduraEspinas = false; EncantamientoArmaduraReparación = false; EncantamientoArmaduraAgilidad = false; EncantamientoArmaduraRecarga = false;

            }
        } else {
            DañoRestanteArmadura = DañoArmadura - Salud[1];
            Salud[1] = 0;
            imprimirPocoAPoco(CYAN + "  - La armadura recibió el daño en tu lugar.", 15);

            EncantamientoArmaduraEspinas_Activo();
            EncantamientoArmaduraReparación_Activo();
            EncantamientoArmaduraAgilidad_Activo();
            EncantamientoArmaduraRecarga_Activo();
            if (ClaseGuerrero){
                imprimirPocoAPoco("  * Daño a armadura: " + DañoAntesReducciónGuerrero, 15);
                imprimirPocoAPoco("  {**} Daño aplicado por resistencia de guerrero: " + DañoArmadura, 15);
            }else {
                imprimirPocoAPoco("  * Daño a armadura: " + DañoArmadura, 15);
            }
            imprimirPocoAPoco(ROJO + "  Tu armadura se ha roto y ha sido eliminada de tu equipamiento." + AMARILLO, 15);
            imprimirPocoAPoco(AMARILLO + "  * Daño restante: " + DañoRestanteArmadura, 15);
            EncantamientoArmaduraEspinas = false; EncantamientoArmaduraReparación = false; EncantamientoArmaduraAgilidad = false; EncantamientoArmaduraRecarga = false;

            procesarJugador();
        }
    }
    private static void procesarJugador() {
        int DañoJugador = 0;
        int DañoAntesReducciónGuerrero = 0;

        if (DañoMagicoEnemigoSN){

            if (DañoRestanteArmadura > 0){
                DañoJugador = DañoRestanteArmadura;
                DañoRestanteArmadura = 0;
            }
            else {
                DañoJugador = DañoMagicoEnemigo;
            }

                imprimirPocoAPoco(MAGENTA + "  * Daño mágico a jugador: " + DañoJugador, 15);

            if (ContadorResistencia[0] > 0) {
                DañoJugador *= 0.5; // Reduce el daño por una poción un 50%
                imprimirPocoAPoco(CYAN + "  {*} Poción de recistencia, reduce el daño mágico: " + DañoJugador, 15);
            }
            Salud[0] -= DañoJugador;
            DañoMagicoEnemigoSN = false;
        }
        else{
            if (DañoRestanteArmadura > 0){
                if (ClaseGuerrero) {
                    DañoAntesReducciónGuerrero = DañoRestanteArmadura;
                    DañoRestanteArmadura *= ReduccionGuerreroDañoObtenido;
                }
                DañoJugador = DañoRestanteArmadura;
                DañoRestanteArmadura = 0;
            }else {
                if (ClaseGuerrero) {
                    DañoAntesReducciónGuerrero = DañoEnemigo;
                    DañoEnemigo *= ReduccionGuerreroDañoObtenido;
                }
                DañoJugador = DañoEnemigo;
            }



            if (ClaseGuerrero){
                imprimirPocoAPoco(AMARILLO + "  * Daño a jugador: " + DañoAntesReducciónGuerrero, 15);
                imprimirPocoAPoco("  {**} Daño aplicado por resistencia de guerrero: " + DañoJugador, 15);

            }else {
                imprimirPocoAPoco(AMARILLO + "  * Daño a jugador: " + DañoJugador, 15);
            }


            if (ContadorResistencia[0] > 0) {
                DañoJugador *= 0.8; // Reduce el daño por una poción un 20%
                imprimirPocoAPoco(CYAN + "  {*} Daño aplicado por poción de resistencia: " + DañoJugador, 15);
            }
            Salud[0] -= DañoJugador;
        }


        if (Salud[0] < 0) {
            Salud[0] = 0;
        }
        imprimirPocoAPoco(AMARILLO + "  * Salud de jugador actual: " + Salud[0] + RESET, 15);
        System.out.println("");
    }




//Fin de codigo------------------------------------------------------
}
