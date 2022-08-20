package me.zeyuan.app.calculator.store

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

@Entity(tableName = "history")
data class HistoryEntity(
    @ColumnInfo(name = "expression") val expression: String,
    @ColumnInfo(name = "result") val result: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "create_at") val createAt: Date? = Date()
)

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history Order By create_at DESC")
    fun getAll(): Flow<List<HistoryEntity>>

//    @Query("SELECT * FROM history WHERE id IN (:id)")
//    fun loadAllByIds(id: Int): Flow<HistoryEntity>

    @Query("SELECT * FROM history WHERE id = :id LIMIT 1")
    fun findById(id: Int): Flow<HistoryEntity>

    @Insert
    fun insert(history: HistoryEntity)

    @Insert
    fun insertAll(vararg historyEntity: HistoryEntity)

    @Delete
    fun delete(historyEntity: HistoryEntity)
}

interface HistoryRepository {
    suspend fun getAll(): Flow<List<HistoryEntity>>
    suspend fun findById(id: Int): Flow<HistoryEntity>
    suspend fun insert(history: HistoryEntity)
    suspend fun delete(history: HistoryEntity)
}

class HistoryRepositoryImpl(private val dao: HistoryDao) : HistoryRepository {
    override suspend fun getAll() = dao.getAll()

    override suspend fun findById(id: Int) = dao.findById(id)

    override suspend fun insert(history: HistoryEntity) = dao.insert(history)

    override suspend fun delete(history: HistoryEntity) = dao.delete(history)

    companion object {
        fun create(context: Context): HistoryRepositoryImpl {
            return HistoryRepositoryImpl(getDatabase(context).historyDao())
        }
    }
}


@Database(entities = [HistoryEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}

//TODO mock
fun getDatabase(applicationContext: Context): AppDatabase {
    return Room.databaseBuilder(applicationContext, AppDatabase::class.java, "calculate")
        .build()
}