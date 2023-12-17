package com.example.chickenquiz
import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.chickenquiz.PlayerData

class QuizDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//base de données
    companion object {
        private const val DATABASE_NAME = "ChickenQuizDB"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME1 = "question_answer_pairs"
        private const val TABLE_NAME2 = "player_levels"
        private const val COLUMN_QUESTION = "question"
        private const val COLUMN_ANSWER_TYPE = "answer_type"
        private const val COLUMN_ANSWERS = "answers"
        private const val COLUMN_CORRECT_ANSWER_INDEX = "correct_answer_index"
        private const val COLUMN_LEVEL = "level"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_MAXLEVEL = "max_level"


    }


    fun initializeQuizQuestions() {  //remplissage de la table quesstions
        val questions = mutableListOf(
            QuestionAnswerPair("Quelle est la principale spécialité de KFC ?", AnswerType.TEXT, listOf("Burger", "Pizza", "Poulet frit", "Sushi"), 2, 3),
            QuestionAnswerPair("Quelle est la devise de KFC ?", AnswerType.TEXT, listOf("Finger Lickin' Good", "Have a Break, Have a Kit Kat", "Just Do It", "I'm Lovin' It"), 0, 3),
            QuestionAnswerPair("Dans quel État américain KFC a-t-il été fondé ?", AnswerType.TEXT, listOf("Kentucky", "Texas", "New York", "California"), 0, 3),
            QuestionAnswerPair("Quel ingrédient secret est utilisé dans la recette originale du poulet de KFC ?", AnswerType.TEXT, listOf("Paprika", "Cumin", "Curry", "11 herbes et épices"), 3, 3),
            QuestionAnswerPair("Quelle partie du poulet est la plus utilisée pour le poulet frit de KFC ?", AnswerType.TEXT, listOf("Ailes", "Cuisses", "Poitrines", "Pattes"), 2, 3),
            QuestionAnswerPair("Combien d'herbes et d'épices sont censées être dans la recette secrète de KFC ?", AnswerType.TEXT, listOf("7", "9", "11", "13"), 2, 3),
            QuestionAnswerPair("Quelle année a marqué l'ouverture du premier restaurant KFC ?", AnswerType.TEXT, listOf("1930", "1952", "1965", "1977"), 1, 3),
            QuestionAnswerPair("Quel fondateur de KFC est l'image emblématique de la marque ?", AnswerType.TEXT, listOf("Colonel Sanders", "Wendy", "Ronald McDonald", "Jack"), 0, 3),
            QuestionAnswerPair("Quelle sauce est traditionnellement servie avec le poulet de KFC ?", AnswerType.TEXT, listOf("Ketchup", "Mayonnaise", "Sauce barbecue", "Sauce au miel moutarde"), 2, 3),
            QuestionAnswerPair("Quel est le processus de cuisson du poulet de KFC ?", AnswerType.TEXT, listOf("Barbecue", "Friture sous pression", "Cuisson au four", "Grillage"), 1, 3),
            QuestionAnswerPair("Quel est le nom complet du Colonel Sanders, fondateur de KFC ?", AnswerType.TEXT, listOf("Harland David Sanders", "John William Sanders", "Thomas Henry Sanders", "George Michael Sanders"), 0, 3),
            QuestionAnswerPair("Dans combien de pays et de territoires KFC est-il présent ?", AnswerType.TEXT, listOf("Plus de 50", "Plus de 100", "Plus de 150", "Plus de 200"), 3, 3),
            QuestionAnswerPair("Combien de calories contient environ un morceau de poulet Original Recipe de KFC ?", AnswerType.TEXT, listOf("150 calories", "250 calories", "350 calories", "450 calories"), 2, 3),


            QuestionAnswerPair("Qui a remporté la Coupe du Monde de la FIFA 2018 ?", AnswerType.TEXT, listOf("Brésil", "Allemagne", "France", "Argentine"), 2, 2),
            QuestionAnswerPair("Quel club de football a remporté le plus de Ligue des champions de l'UEFA ?", AnswerType.TEXT, listOf("Real Madrid", "FC Barcelone", "Manchester United", "AC Milan"), 0, 2),
            QuestionAnswerPair("Quel joueur a remporté le Ballon d'Or 2020 ?", AnswerType.TEXT, listOf("Lionel Messi", "Cristiano Ronaldo", "Robert Lewandowski", "Neymar"), 2, 2),
            QuestionAnswerPair("Qui a été le meilleur buteur de la Premier League anglaise lors de la saison 2020-2021 ?", AnswerType.TEXT, listOf("Harry Kane", "Mohamed Salah", "Bruno Fernandes", "Jamie Vardy"), 1, 2),
            QuestionAnswerPair("Quelle équipe a remporté le plus de titres de la Copa America ?", AnswerType.TEXT, listOf("Argentine", "Brésil", "Uruguay", "Paraguay"), 2, 2),
            QuestionAnswerPair("Qui a été le meilleur buteur de tous les temps en Coupe du Monde de la FIFA ?", AnswerType.TEXT, listOf("Miroslav Klose", "Pele", "Ronaldo Nazário", "Lionel Messi"), 0, 2),
            QuestionAnswerPair("Quel joueur a remporté le plus de Souliers d'Or européens ?", AnswerType.TEXT, listOf("Lionel Messi", "Cristiano Ronaldo", "Neymar", "Robert Lewandowski"), 1, 2),
            QuestionAnswerPair("Quel club a remporté le plus de titres de la Serie A italienne ?", AnswerType.TEXT, listOf("AC Milan", "Inter Milan", "Juventus", "AS Roma"), 2, 2),
            QuestionAnswerPair("Combien de fois le Brésil a-t-il remporté la Coupe du Monde de la FIFA ?", AnswerType.TEXT, listOf("4 fois", "5 fois", "6 fois", "7 fois"), 2, 2),
            QuestionAnswerPair("Quel joueur a remporté le plus de Ballons d'Or ?", AnswerType.TEXT, listOf("Lionel Messi", "Cristiano Ronaldo", "Michel Platini", "Johan Cruyff"), 0, 2),
            QuestionAnswerPair("Quel club a remporté le plus de titres de la Bundesliga allemande ?", AnswerType.TEXT, listOf("Bayern Munich", "Borussia Dortmund", "Hambourg SV", "Werder Brême"), 0, 2),
            QuestionAnswerPair("Qui est le seul joueur à avoir remporté le Ballon d'Or avec trois clubs différents ?", AnswerType.TEXT, listOf("Lionel Messi", "Cristiano Ronaldo", "Franz Beckenbauer", "Luis Suarez"), 2, 2),
            QuestionAnswerPair("Quel joueur détient le record du plus grand nombre de buts marqués au cours d'une seule édition de la Ligue des champions de l'UEFA ?", AnswerType.TEXT, listOf("Lionel Messi", "Cristiano Ronaldo", "Raul", "Ferenc Puskas"), 2, 2),
            QuestionAnswerPair("Quelle équipe nationale a remporté la Coupe d'Afrique des Nations le plus grand nombre de fois ?", AnswerType.TEXT, listOf("Nigeria", "Côte d'Ivoire", "Ghana", "Egypte"), 3, 2),
            QuestionAnswerPair("Qui est le seul gardien de but à avoir remporté le Ballon d'Or ?", AnswerType.TEXT, listOf("Lev Yachine", "Iker Casillas", "Gianluigi Buffon", "Manuel Neuer"), 0, 2),

            QuestionAnswerPair("Quel est le plus haut sommet d'Algérie ?", AnswerType.TEXT, listOf("Tahat", "Lalla Khedidja", "Mont Chelia", "Mont Tassili"), 0, 1),
            QuestionAnswerPair("En quelle année l'Algérie a-t-elle obtenu son indépendance ?", AnswerType.TEXT, listOf("1962", "1954", "1970", "1980"), 0, 1),
            QuestionAnswerPair("Quel écrivain algérien a remporté le prix Nobel de littérature en 1957 ?", AnswerType.TEXT, listOf("Albert Camus", "Assia Djebar", "Kateb Yacine", "Mouloud Mammeri"), 1, 1),
            QuestionAnswerPair("Quelle est la date de la fête nationale de l'Algérie ?", AnswerType.TEXT, listOf("5 juillet", "1er novembre", "19 mars", "22 février"), 0, 1),
            QuestionAnswerPair("Quel est le nom du parc national situé dans le Sahara algérien ?", AnswerType.TEXT, listOf("Parc national du Tassili n'Ajjer", "Parc national de Gouraya", "Parc national de Belezma", "Parc national de Theniet El Had"), 0, 1),
            QuestionAnswerPair("Quelle est la superficie totale de l'Algérie en kilomètres carrés ?", AnswerType.TEXT, listOf("2,381,741 km²", "1,234,567 km²", "3,456,789 km²", "4,567,890 km²"), 0, 1),
            QuestionAnswerPair("Quel est le plus grand port d'Algérie ?", AnswerType.TEXT, listOf("Port d'Alger", "Port d'Oran", "Port d'Annaba", "Port de Béjaïa"), 0, 1),
            QuestionAnswerPair("Qui était le président de l'Algérie pendant la guerre d'indépendance ?", AnswerType.TEXT, listOf("Ahmed Ben Bella", "Houari Boumédiène", "Abdelaziz Bouteflika", "Bachir Boumaza"), 0, 1),
            QuestionAnswerPair("Quelle est la plus ancienne ville d'Algérie ?", AnswerType.TEXT, listOf("Timgad", "Tipaza", "Annaba", "Constantine"), 0, 1),
            QuestionAnswerPair("Quelle est la durée totale de la guerre d'indépendance algérienne ?", AnswerType.TEXT, listOf("8 ans", "10 ans", "12 ans", "15 ans"), 1, 1),
            QuestionAnswerPair("Qui est le réalisateur du film 'La Bataille d'Alger' ?", AnswerType.TEXT, listOf("Gillo Pontecorvo", "Youssef Chahine", "Rachid Bouchareb", "Costa-Gavras"), 0, 1),
            QuestionAnswerPair("Quel est le nom du célèbre musicien algérien surnommé 'le Rossignol du Raï' ?", AnswerType.TEXT, listOf("Cheb Khaled", "Cheb Mami", "Cheikha Rimitti", "Rachid Taha"), 0, 1),
            QuestionAnswerPair("Quelle est la date du premier novembre, jour de la Toussaint rouge en Algérie ?", AnswerType.TEXT, listOf("1954", "1956", "1958", "1960"), 0, 1),
            QuestionAnswerPair("Quel est le nom du groupe rebelle algérien pendant la guerre d'indépendance ?", AnswerType.TEXT, listOf("FLN (Front de libération nationale)", "MNA (Mouvement national algérien)", "ALN (Armée de libération nationale)", "PCA (Parti communiste algérien)"), 0, 1),
            QuestionAnswerPair("Quel est le nom du stade national d'Algérie ?", AnswerType.TEXT, listOf("Stade du 5 Juillet", "Stade Mustapha Tchaker", "Stade Ahmed Zabana", "Stade Omar Hamadi"), 0, 1) ,

            QuestionAnswerPair("Quelle est la capitale du Canada ?", AnswerType.TEXT, listOf("Ottawa", "Toronto", "Vancouver", "Montréal"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale du Japon ?", AnswerType.TEXT, listOf("Tokyo", "Osaka", "Kyoto", "Seoul"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale de l'Australie ?", AnswerType.TEXT, listOf("Canberra", "Sydney", "Melbourne", "Brisbane"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale du Brésil ?", AnswerType.TEXT, listOf("Brasilia", "Rio de Janeiro", "São Paulo", "Salvador"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale de l'Égypte ?", AnswerType.TEXT, listOf("Le Caire", "Alexandrie", "Louxor", "Gizeh"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale de l'Afrique du Sud ?", AnswerType.TEXT, listOf("Pretoria", "Le Cap", "Johannesburg", "Durban"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale de la Turquie ?", AnswerType.TEXT, listOf("Ankara", "Istanbul", "Izmir", "Antalya"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale de la Russie ?", AnswerType.TEXT, listOf("Moscou", "Saint-Pétersbourg", "Kazan", "Novossibirsk"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale du Mexique ?", AnswerType.TEXT, listOf("Mexico", "Guadalajara", "Monterrey", "Puebla"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale de l'Inde ?", AnswerType.TEXT, listOf("New Delhi", "Mumbai", "Kolkata", "Chennai"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale du Kazakhstan ?", AnswerType.TEXT, listOf("Astana", "Almaty", "Nur-Sultan", "Karaganda"), 2, 4),
            QuestionAnswerPair("Quelle est la capitale du Bhoutan ?", AnswerType.TEXT, listOf("Thimphou", "Paro", "Punakha", "Jakar"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale de la Mongolie ?", AnswerType.TEXT, listOf("Oulan-Bator", "Darkhan", "Erdenet", "Choibalsan"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale du Lesotho ?", AnswerType.TEXT, listOf("Maseru", "Mafeteng", "Leribe", "Mohale's Hoek"), 0, 4),
            QuestionAnswerPair("Quelle est la capitale du Suriname ?", AnswerType.TEXT, listOf("Paramaribo", "Nieuw Nickerie", "Lelydorp", "Moengo"), 0, 4),

            )


        // Insertion
        for (question in questions) {
            if (!questionExists(question)) {
                insertQuestionAnswerPair(question)
            }
        }

    }

    private fun clearQuestionsForLevel(level: Int) {
        val db = writableDatabase
        val deleteQuery = "DELETE FROM $TABLE_NAME1 WHERE $COLUMN_LEVEL = ?"
        db.execSQL(deleteQuery, arrayOf(level.toString()))
        db.close()
    }


    @SuppressLint("Range")
    fun getPlayerData(name: String): PlayerData? {  //recuperation des données du joeur
        val db = readableDatabase
        var playerData: PlayerData? = null

        val query = "SELECT * FROM $TABLE_NAME2 WHERE $COLUMN_NAME = ?"
        val cursor = db.rawQuery(query, arrayOf(name))

        if (cursor.moveToFirst()) {
            val maxLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_MAXLEVEL))
            playerData = PlayerData(name, maxLevel)
        }

        cursor.close()
        db.close()

        return playerData
    }



    private fun questionExists(question: QuestionAnswerPair): Boolean { //verification existence queistionS
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME1 WHERE $COLUMN_QUESTION = ? AND $COLUMN_LEVEL = ?"
        val cursor = db.rawQuery(query, arrayOf(question.question, question.level.toString()))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }




    override fun onCreate(db: SQLiteDatabase?) { //creation des deux tables
        val createTableQuery = """
        CREATE TABLE $TABLE_NAME1 (
            $COLUMN_QUESTION TEXT,
            $COLUMN_ANSWER_TYPE TEXT,
            $COLUMN_ANSWERS TEXT,
            $COLUMN_CORRECT_ANSWER_INDEX INTEGER,
            $COLUMN_LEVEL INTEGER
        )
    """.trimIndent()

        val createTableQuery2 = """
        CREATE TABLE $TABLE_NAME2 (
            $COLUMN_NAME TEXT,
            $COLUMN_MAXLEVEL INTEGER
        )
    """.trimIndent()

        db?.execSQL(createTableQuery)
        db?.execSQL(createTableQuery2)
    }



    private fun createPlayerLevelsTable() {  //fonction test pour creeer des joeurx ayant finit le jeu
        val db = writableDatabase

        val createTableQuery2 = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME2 (
                $COLUMN_NAME TEXT,
                $COLUMN_MAXLEVEL INTEGER
            )
        """.trimIndent()

        db.execSQL(createTableQuery2)
        db.close()
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Supprimer les tables existantes et appelez onCreate pour recréer la base de données
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME1")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
        onCreate(db)
    }



    fun insertQuestionAnswerPair(pair: QuestionAnswerPair) { //insertion
        val db = writableDatabase

        val insertQuery = "INSERT INTO $TABLE_NAME1 VALUES (?, ?, ?, ?, ?)"
        val values = arrayOf(
            pair.question,
            pair.answerType.toString(),
            pair.answers.joinToString(","),
            pair.correctAnswerIndex.toString(),
            pair.level.toString()
        )

        db.execSQL(insertQuery, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getQuestionsForLevel(level: Int): List<QuestionAnswerPair> {  //recuperation de toutes les paires questions reponse d'un niveau
        val db = readableDatabase
        val pairs = mutableListOf<QuestionAnswerPair>()

        val query = "SELECT * FROM $TABLE_NAME1 WHERE $COLUMN_LEVEL = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(level.toString()))

        if (cursor.moveToFirst()) {
            do {
                val question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION))
                val answerType = AnswerType.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER_TYPE)))
                val answers = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWERS)).split(",")
                val correctAnswerIndex = cursor.getInt(cursor.getColumnIndex(COLUMN_CORRECT_ANSWER_INDEX))
                val pairLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL))

                val pair = QuestionAnswerPair(question, answerType, answers, correctAnswerIndex, pairLevel)
                pairs.add(pair)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return pairs
    }



    @SuppressLint("Range")
    fun getAllPlayers(): List<PlayerData> { //réuperation de tout les joeurs
        val db = readableDatabase
        val players = mutableListOf<PlayerData>()

        val query = "SELECT * FROM $TABLE_NAME2"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val maxLevel = cursor.getInt(cursor.getColumnIndex(COLUMN_MAXLEVEL))
                val playerData = PlayerData(name, maxLevel)
                players.add(playerData)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return players
    }



    fun insertPlayer(name: String) {   //insertion des nouveaux joueurs
        val db = writableDatabase

        val insertQuery = "INSERT INTO $TABLE_NAME2 VALUES (?, 4)" //niveau 4 pour tout tester
        val values = arrayOf(name)

        db.execSQL(insertQuery, values)
        db.close()
    }

    fun deleteAllPlayers() {
        val db = writableDatabase

        val deleteQuery = "DELETE FROM $TABLE_NAME2"

        db.execSQL(deleteQuery)
        db.close()
    }

    fun updatePlayerMaxLevel(name: String, newMaxLevel: Int) { //mise à jour selon réussite du joueur
        val db = writableDatabase

        val updateQuery = "UPDATE $TABLE_NAME2 SET $COLUMN_MAXLEVEL = ? WHERE $COLUMN_NAME = ?"
        val values = arrayOf(newMaxLevel.toString(), name)

        db.execSQL(updateQuery, values)
        db.close()
    }

}
