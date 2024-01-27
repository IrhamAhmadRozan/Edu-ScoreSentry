package com.developer.rozan.eduscoresentry.util.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.developer.rozan.eduscoresentry.data.entity.Guru
import com.developer.rozan.eduscoresentry.data.entity.Kehadiran
import com.developer.rozan.eduscoresentry.data.entity.KehadiranDetail
import com.developer.rozan.eduscoresentry.data.entity.Kelas
import com.developer.rozan.eduscoresentry.data.entity.KelasMember
import com.developer.rozan.eduscoresentry.data.entity.Nilai
import com.developer.rozan.eduscoresentry.data.entity.NilaiDetail
import com.developer.rozan.eduscoresentry.data.entity.Siswa

class DatabaseHelper(var context: Context) :
    SQLiteOpenHelper(context, "edu_score_sentry", null, 1) {

    private val db = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {

        // Todo Table Master Siswa
        val createMsSiswa = "CREATE TABLE ms_siswa ( " +
                " nisn VARCHAR(50) PRIMARY KEY, " +
                " nama_siswa VARCHAR(50), " +
                " no_telp VARCHAR(15), " +
                " alamat TEXT, " +
                " password VARCHAR(50))"
        db?.execSQL(createMsSiswa)

        db?.execSQL(
            "INSERT INTO ms_siswa(nisn, nama_siswa, no_telp, alamat, password) " +
                    "VALUES ('202031084', 'Rachel Tarigan', '123409874567', 'Jl. Nanas No. 51', 'siswa1')"
        )
        db?.execSQL(
            "INSERT INTO ms_siswa(nisn, nama_siswa, no_telp, alamat, password) " +
                    "VALUES ('202131027', 'Irham Ahmad Rozan', '082109871234', 'Jl. Anggur No. 34', 'siswa2')"
        )
        db?.execSQL(
            "INSERT INTO ms_siswa(nisn, nama_siswa, no_telp, alamat, password) " +
                    "VALUES ('202131053', 'Nursalsabila Ali', '085712340987', 'Jl. Semangka No. 41', 'siswa3')"
        )
        db?.execSQL(
            "INSERT INTO ms_siswa(nisn, nama_siswa, no_telp, alamat, password) " +
                    "VALUES ('202131055', 'Weni Octavia', '081298762345', 'Jl. Durian No. 25', 'siswa4')"
        )

        // Todo Table Master Guru
        val createMsGuru = "CREATE TABLE ms_guru ( " +
                " nip VARCHAR(50) PRIMARY KEY, " +
                " nama_guru VARCHAR(50), " +
                " email VARCHAR(50), " +
                " no_telp VARCHAR(15), " +
                " alamat TEXT, " +
                " password VARCHAR(50))"
        db?.execSQL(createMsGuru)

        db?.execSQL(
            "INSERT INTO ms_guru(nip, nama_guru, email, no_telp, alamat, password) " +
                    "VALUES ('1111', 'Pritasari Palupiningsih', 'guru1@gmail.com', '082112121212', 'Jl. Mawar No. 1', 'guru1')"
        )
        db?.execSQL(
            "INSERT INTO ms_guru(nip, nama_guru, email, no_telp, alamat, password) " +
                    "VALUES ('2222', 'Budi Prayitno', 'guru2@gmail.com', '082123232323', 'Jl. Anggrek No. 65', 'guru2')"
        )

        // Todo Table Master Mata Pelajaran
        val createMsMataPelajaran = "CREATE TABLE ms_mata_pelajaran ( " +
                " id_mata_pelajaran VARCHAR(50) PRIMARY KEY, " +
                " nama_mata_pelajaran VARCHAR(50))"
        db?.execSQL(createMsMataPelajaran)

        db?.execSQL(
            "INSERT INTO ms_mata_pelajaran(id_mata_pelajaran, nama_mata_pelajaran) " +
                    "VALUES ('mp-1', 'Pendidikan Kewarnegaraan')"
        )
        db?.execSQL(
            "INSERT INTO ms_mata_pelajaran(id_mata_pelajaran, nama_mata_pelajaran) " +
                    "VALUES ('mp-2', 'Ilmu Pengetahuan Sosial')"
        )
        db?.execSQL(
            "INSERT INTO ms_mata_pelajaran(id_mata_pelajaran, nama_mata_pelajaran) " +
                    "VALUES ('mp-3', 'Matematika')"
        )

        // Todo Table Transaction Kelas
        val createTxKelas = "CREATE TABLE tx_kelas ( " +
                " id_kelas VARCHAR(50) PRIMARY KEY, " +
                " id_mata_pelajaran VARCHAR(50), " +
                " nip VARCHAR(50), " +
                " nama_kelas VARCHAR(50), " +
                " jadwal_kelas VARCHAR(50), " +
                " FOREIGN KEY(id_mata_pelajaran) references ms_mata_pelajaran(id_mata_pelajaran), " +
                " FOREIGN KEY(nip) references ms_guru(nip))"
        db?.execSQL(createTxKelas)

        db?.execSQL(
            "INSERT INTO tx_kelas(id_kelas, id_mata_pelajaran, nip, nama_kelas, jadwal_kelas) " +
                    "VALUES ('kelas-1', 'mp-1', '1111', 'Kelas PKN A', 'Senin, 07.00 - 08.40')"
        )

        db?.execSQL(
            "INSERT INTO tx_kelas(id_kelas, id_mata_pelajaran, nip, nama_kelas, jadwal_kelas) " +
                    "VALUES ('kelas-2', 'mp-2', '1111', 'Kelas IPS A', 'Selasa, 07.00 - 08.40')"
        )

        db?.execSQL(
            "INSERT INTO tx_kelas(id_kelas, id_mata_pelajaran, nip, nama_kelas, jadwal_kelas) " +
                    "VALUES ('kelas-3', 'mp-3', '2222', 'Kelas MTK A', 'Senin, 09.00 - 10.40')"
        )

        // Todo Table Transaction Kelas Member
        val createTxKelasMember = "CREATE TABLE tx_kelas_member ( " +
                " id_kelas VARCHAR(50), " +
                " nisn VARCHAR(50), " +
                " FOREIGN KEY(id_kelas) references tx_kelas(id_kelas), " +
                " FOREIGN KEY(nisn) references ms_siswa(nisn))"
        db?.execSQL(createTxKelasMember)

        db?.execSQL(
            "INSERT INTO tx_kelas_member(id_kelas, nisn) " +
                    "VALUES ('kelas-1', '202131027')"
        )
        db?.execSQL(
            "INSERT INTO tx_kelas_member(id_kelas, nisn) " +
                    "VALUES ('kelas-1', '202131053')"
        )

        db?.execSQL(
            "INSERT INTO tx_kelas_member(id_kelas, nisn) " +
                    "VALUES ('kelas-2', '202131053')"
        )
        db?.execSQL(
            "INSERT INTO tx_kelas_member(id_kelas, nisn) " +
                    "VALUES ('kelas-2', '202131055')"
        )

        db?.execSQL(
            "INSERT INTO tx_kelas_member(id_kelas, nisn) " +
                    "VALUES ('kelas-3', '202131055')"
        )
        db?.execSQL(
            "INSERT INTO tx_kelas_member(id_kelas, nisn) " +
                    "VALUES ('kelas-3', '202131027')"
        )

        // Todo Table Transaction Nilai
        val createTxNilai = "CREATE TABLE tx_nilai ( " +
                " id_nilai VARCHAR(50) PRIMARY KEY, " +
                " id_kelas VARCHAR(50), " +
                " judul_nilai TEXT, " +
                " FOREIGN KEY(id_kelas) references tx_kelas(id_kelas))"
        db?.execSQL(createTxNilai)

        // Todo Table Transaction Nilai Detail
        val createTxNilaiDetail = "CREATE TABLE tx_nilai_detail ( " +
                " id_nilai VARCHAR(50), " +
                " nisn VARCHAR(50), " +
                " status_nilai INTEGER, " +
                " FOREIGN KEY(id_nilai) references tx_nilai(id_nilai), " +
                " FOREIGN KEY(nisn) references ms_siswa(nisn))"
        db?.execSQL(createTxNilaiDetail)

        // Todo Table Transaction Kehadiran
        val createTxKehadiran = "CREATE TABLE tx_kehadiran ( " +
                " id_kehadiran VARCHAR(50) PRIMARY KEY, " +
                " id_kelas VARCHAR(50), " +
                " judul_kehadiran TEXT, " +
                " FOREIGN KEY(id_kelas) references tx_kelas(id_kelas))"
        db?.execSQL(createTxKehadiran)

        // Todo Table Transaction Kehadiran Detail
        val createTxKehadiranDetail = "CREATE TABLE tx_kehadiran_detail ( " +
                " id_kehadiran VARCHAR(50), " +
                " nisn VARCHAR(50), " +
                " status_kehadiran VARCHAR(50), " +
                " FOREIGN KEY(id_kehadiran) references tx_kehadiran(id_kehadiran), " +
                " FOREIGN KEY(nisn) references ms_siswa(nisn))"
        db?.execSQL(createTxKehadiranDetail)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    fun guruLogin(nip: String, password: String): String {
        val query = "SELECT * FROM ms_guru WHERE nip='$nip' AND password='$password'"
        val rs = db.rawQuery(query, null)
        if (rs.moveToFirst()) {
            val token = rs.getString(rs.getColumnIndex("nip"))
            rs.close()

            return token
        }
        return ""
    }

    @SuppressLint("Range")
    fun siswaLogin(nisn: String, password: String): String {
        val query = "SELECT * FROM ms_siswa WHERE nisn='$nisn' AND password='$password'"
        val rs = db.rawQuery(query, null)
        if (rs.moveToFirst()) {
            val token = rs.getString(rs.getColumnIndex("nisn"))
            rs.close()

            return token
        }
        return ""
    }

    fun getGuruByNIP(nip: String): Guru? {
        var guru: Guru? = null
        val sql = "SELECT * FROM ms_guru WHERE nip='$nip'"
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            guru = Guru(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
            )
        }
        return guru
    }

    fun getSiswaByNISN(nisn: String): Siswa? {
        var siswa: Siswa? = null
        val sql = "SELECT * FROM ms_siswa WHERE nisn='$nisn'"
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            siswa = Siswa(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
            )
        }
        return siswa
    }

    @SuppressLint("Range")
    fun getListKelasByNIP(nip: String): List<Kelas> {
        val kelasList: MutableList<Kelas> = ArrayList()
        val sql =
            "SELECT a.id_kelas, a.id_mata_pelajaran, b.nama_mata_pelajaran, a.nip, a.nama_kelas, a.jadwal_kelas" +
                    " FROM tx_kelas a" +
                    " INNER JOIN ms_mata_pelajaran b ON a.id_mata_pelajaran = b.id_mata_pelajaran" +
                    " WHERE nip='$nip' "
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val kelas = Kelas(
                    cursor.getString(cursor.getColumnIndex(Kelas.ID_KELAS)),
                    cursor.getString(cursor.getColumnIndex(Kelas.ID_MATA_PELAJARAN)),
                    cursor.getString(cursor.getColumnIndex(Kelas.NAMA_MATA_PELAJARAN)),
                    cursor.getString(cursor.getColumnIndex(Kelas.NIP)),
                    cursor.getString(cursor.getColumnIndex(Kelas.NAMA_KELAS)),
                    cursor.getString(cursor.getColumnIndex(Kelas.JADWAL_KELAS))
                )
                kelasList.add(kelas)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return kelasList
    }

    @SuppressLint("Range")
    fun getListKelasAndKelasMemberByNISN(nisn: String): List<Kelas> {
        val kelasList: MutableList<Kelas> = ArrayList()
        val sql =
            "SELECT a.id_kelas, a.nisn, b.id_mata_pelajaran, c.nama_mata_pelajaran, b.nama_kelas, b.jadwal_kelas" +
                    " FROM tx_kelas_member a" +
                    " INNER JOIN tx_kelas b ON a.id_kelas = b.id_kelas" +
                    " INNER JOIN ms_mata_pelajaran c ON b.id_mata_pelajaran = c.id_mata_pelajaran" +
                    " WHERE nisn='$nisn' "
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val kelasMember = KelasMember(
                    cursor.getString(cursor.getColumnIndex(KelasMember.ID_KELAS)),
                    cursor.getString(cursor.getColumnIndex(KelasMember.NISN))
                )
                val kelas = Kelas(
                    cursor.getString(cursor.getColumnIndex(Kelas.ID_KELAS)),
                    cursor.getString(cursor.getColumnIndex(Kelas.ID_MATA_PELAJARAN)),
                    cursor.getString(cursor.getColumnIndex(Kelas.NAMA_MATA_PELAJARAN)),
                    "",
                    cursor.getString(cursor.getColumnIndex(Kelas.NAMA_KELAS)),
                    cursor.getString(cursor.getColumnIndex(Kelas.JADWAL_KELAS)),
                    kelasMember
                )
                kelasList.add(kelas)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return kelasList
    }

    @SuppressLint("Range")
    fun getListKelasMemberByIdKelas(id_kelas: String): List<KelasMember> {
        val kelasMemberList: MutableList<KelasMember> = ArrayList()
        val sql = "SELECT a.id_kelas, a.nisn, b.nama_siswa" +
                " FROM tx_kelas_member a" +
                " INNER JOIN ms_siswa b ON a.nisn = b.nisn" +
                " WHERE id_kelas='$id_kelas' "
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val kelasMember = KelasMember(
                    cursor.getString(cursor.getColumnIndex(KelasMember.ID_KELAS)),
                    cursor.getString(cursor.getColumnIndex(KelasMember.NISN)),
                    cursor.getString(cursor.getColumnIndex(KelasMember.NAMA_SISWA))
                )
                kelasMemberList.add(kelasMember)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return kelasMemberList
    }

    fun getListNilaiByIdKelas(id_kelas: String): List<Nilai> {
        val nilaiList: MutableList<Nilai> = ArrayList()
        val sql = "SELECT * FROM tx_nilai WHERE id_kelas='$id_kelas' "
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val nilai = Nilai(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
                nilaiList.add(nilai)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return nilaiList
    }

    @SuppressLint("Range")
    fun getListNilaiAndNilaiDetailByIdKelasAndNISN(id_kelas: String, nisn: String): List<Nilai> {
        val nilaiList: MutableList<Nilai> = ArrayList()
        val sql = "SELECT a.id_nilai, a.id_kelas, a.judul_nilai, b.nisn, b.status_nilai" +
                " FROM tx_nilai a" +
                " INNER JOIN tx_nilai_detail b ON a.id_nilai = b.id_nilai" +
                " WHERE a.id_kelas='$id_kelas' AND b.nisn='$nisn' "
//        val sql = "SELECT a.id_nilai, a.id_kelas, a.judul_nilai, b.nisn, c.nama_siswa, b.status_nilai" +
//                " FROM tx_nilai a" +
//                " INNER JOIN tx_nilai_detail b ON a.id_nilai = b.id_nilai WHERE nisn='$nisn' " +
//                " INNER JOIN ms_siswa c ON b.nisn = c.nisn" +
//                " WHERE id_kelas='$id_kelas' "
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val nilaiDetail = NilaiDetail(
                    cursor.getString(cursor.getColumnIndex(NilaiDetail.ID_NILAI)),
                    cursor.getString(cursor.getColumnIndex(NilaiDetail.NISN)),
                    "",
                    cursor.getInt(cursor.getColumnIndex(NilaiDetail.STATUS_NILAI))
                )
                val nilai = Nilai(
                    cursor.getString(cursor.getColumnIndex(Nilai.ID_NILAI)),
                    cursor.getString(cursor.getColumnIndex(Nilai.ID_KELAS)),
                    cursor.getString(cursor.getColumnIndex(Nilai.JUDUL_NILAI)),
                    nilaiDetail
                )
                nilaiList.add(nilai)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return nilaiList
    }

    @SuppressLint("Range")
    fun getListKehadiranAndKehadiranDetailByIdKelasAndNISN(
        id_kelas: String,
        nisn: String
    ): List<Kehadiran> {
        val kehadiranList: MutableList<Kehadiran> = ArrayList()
        val sql =
            "SELECT a.id_kehadiran, a.id_kelas, a.judul_kehadiran, b.nisn, b.status_kehadiran" +
                    " FROM tx_kehadiran a" +
                    " INNER JOIN tx_kehadiran_detail b ON a.id_kehadiran = b.id_kehadiran" +
                    " WHERE a.id_kelas='$id_kelas' AND b.nisn='$nisn' "
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val kehadiranDetail = KehadiranDetail(
                    cursor.getString(cursor.getColumnIndex(KehadiranDetail.ID_KEHADIRAN)),
                    cursor.getString(cursor.getColumnIndex(KehadiranDetail.NISN)),
                    "",
                    cursor.getString(cursor.getColumnIndex(KehadiranDetail.STATUS_KEHADIRAN))
                )
                val kehadiran = Kehadiran(
                    cursor.getString(cursor.getColumnIndex(Kehadiran.ID_KEHADIRAN)),
                    cursor.getString(cursor.getColumnIndex(Kehadiran.ID_KELAS)),
                    cursor.getString(cursor.getColumnIndex(Kehadiran.JUDUL_KEHADIRAN)),
                    kehadiranDetail
                )
                kehadiranList.add(kehadiran)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return kehadiranList
    }

    fun getListKehadiranByIdKelas(id_kelas: String): List<Kehadiran> {
        val kehadiranList: MutableList<Kehadiran> = ArrayList()
        val sql = "SELECT * FROM tx_kehadiran WHERE id_kelas='$id_kelas' "
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val kehadiran = Kehadiran(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
                kehadiranList.add(kehadiran)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return kehadiranList
    }

    fun getListNilaiDetailByIdNilai(id_nilai: String): List<NilaiDetail> {
        val nilaiDetailList: MutableList<NilaiDetail> = ArrayList()
        val sql = "SELECT a.id_nilai, a.nisn, b.nama_siswa, a.status_nilai" +
                " FROM tx_nilai_detail a" +
                " INNER JOIN ms_siswa b ON a.nisn = b.nisn" +
                " WHERE id_nilai ='$id_nilai' "
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val nilaiDetail = NilaiDetail(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3)
                )
                nilaiDetailList.add(nilaiDetail)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return nilaiDetailList
    }

    fun getListKehadiranDetailByIdNilai(id_kehadiran: String): List<KehadiranDetail> {
        val kehadiranDetailList: MutableList<KehadiranDetail> = ArrayList()
        val sql = "SELECT a.id_kehadiran, a.nisn, b.nama_siswa, a.status_kehadiran" +
                " FROM tx_kehadiran_detail a" +
                " INNER JOIN ms_siswa b ON a.nisn = b.nisn" +
                " WHERE id_kehadiran ='$id_kehadiran' "
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val kehadiranDetail = KehadiranDetail(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                )
                kehadiranDetailList.add(kehadiranDetail)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return kehadiranDetailList
    }

    fun insertTxNilai(nilai: Nilai): Long {
        val cv = ContentValues()
        cv.put(Nilai.ID_NILAI, nilai.id_nilai)
        cv.put(Nilai.ID_KELAS, nilai.id_kelas)
        cv.put(Nilai.JUDUL_NILAI, nilai.judul_nilai)
        return db.insert(Nilai.TABLE_TX_NILAI, null, cv)
    }

    fun insertTxNilaiDetail(nilaiDetail: NilaiDetail): Long {
        val cv = ContentValues()
        cv.put(NilaiDetail.ID_NILAI, nilaiDetail.id_nilai)
        cv.put(NilaiDetail.NISN, nilaiDetail.nisn)
        cv.put(NilaiDetail.STATUS_NILAI, nilaiDetail.status_nilai)
        return db.insert(NilaiDetail.TABLE_TX_NILAI_DETAIL, null, cv)
    }

    fun insertTxKehadiran(kehadiran: Kehadiran): Long {
        val cv = ContentValues()
        cv.put(Kehadiran.ID_KEHADIRAN, kehadiran.id_kehadiran)
        cv.put(Kehadiran.ID_KELAS, kehadiran.id_kelas)
        cv.put(Kehadiran.JUDUL_KEHADIRAN, kehadiran.judul_kehadiran)
        return db.insert(Kehadiran.TABLE_TX_KEHADIRAN, null, cv)
    }

    fun insertTxKehadiranDetail(kehadiranDetail: KehadiranDetail): Long {
        val cv = ContentValues()
        cv.put(KehadiranDetail.ID_KEHADIRAN, kehadiranDetail.id_kehadiran)
        cv.put(KehadiranDetail.NISN, kehadiranDetail.nisn)
        cv.put(KehadiranDetail.STATUS_KEHADIRAN, kehadiranDetail.status_kehadiran)
        return db.insert(KehadiranDetail.TABLE_TX_KEHADIRAN_DETAIL, null, cv)
    }

    fun updateTxNilai(nilai: Nilai): Int {
        val cv = ContentValues()
        cv.put(Nilai.JUDUL_NILAI, nilai.judul_nilai)
        return db.update(Nilai.TABLE_TX_NILAI, cv, "${Nilai.ID_NILAI}=?", arrayOf(nilai.id_nilai))
    }

    fun updateTxNilaiDetail(nilaiDetail: NilaiDetail): Int {
        val cv = ContentValues()
        cv.put(NilaiDetail.STATUS_NILAI, nilaiDetail.status_nilai)
        return db.update(
            NilaiDetail.TABLE_TX_NILAI_DETAIL,
            cv,
            "${NilaiDetail.ID_NILAI}=? AND ${NilaiDetail.NISN}=?",
            arrayOf(nilaiDetail.id_nilai, nilaiDetail.nisn)
        )
    }

    fun updateTxKehadiran(kehadiran: Kehadiran): Int {
        val cv = ContentValues()
        cv.put(Kehadiran.JUDUL_KEHADIRAN, kehadiran.judul_kehadiran)
        return db.update(
            Kehadiran.TABLE_TX_KEHADIRAN,
            cv,
            "${Kehadiran.ID_KEHADIRAN}=?",
            arrayOf(kehadiran.id_kehadiran)
        )
    }

    fun updateTxKehadiranDetail(kehadiranDetail: KehadiranDetail): Int {
        val cv = ContentValues()
        cv.put(KehadiranDetail.STATUS_KEHADIRAN, kehadiranDetail.status_kehadiran)
        return db.update(
            KehadiranDetail.TABLE_TX_KEHADIRAN_DETAIL,
            cv,
            "${KehadiranDetail.ID_KEHADIRAN}=? AND ${KehadiranDetail.NISN}=?",
            arrayOf(kehadiranDetail.id_kehadiran, kehadiranDetail.nisn)
        )
    }
}