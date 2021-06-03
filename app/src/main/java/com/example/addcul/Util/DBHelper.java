package com.example.addcul.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSION = 83;

    public DBHelper(Context context) {
        super(context, "addculDB", null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

//        신조어 테이블
        String wordSQL = "create table newWord(" +
                "_id integer primary key autoincrement," +
                "num text," +
                "name text," +
                "info text," +
                "subInfo text);";
        db.execSQL(wordSQL);

        db.execSQL("insert into newWord(num, name, info, subInfo)values ('1','군싹', '[ 군침이 싹 돈다 ]', '음식이 맛있어 보여 군침이 돈다는 것을 줄인 말이에요!\n맛있는 음식을 봤을 때 사용해보아요!')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('2','스불재', '[ 스스로 불러온 재앙 ]', '-')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('3','주불', '[ 주소 불러 ]', '-')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('4','재질', '[ 느낌, 스타일, 부류 ]', '-')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('5','머선129', '[ 무슨 일이야? ]', '-')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('6','억텐', '[ 억지 텐션 ]', '-')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('7','임포', '[ 스파이 | 배신자 ]', '-')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('8','쫌쫌따리', '[ 조금씩 틈틈히 모으는 모양 ]', '-')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('9','갑통알', '[ 갑자기 통장을 보니 알바해야겠다 ]', '-')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('10','애빼시', '[ 애교 빼면 시체 ]', '-')");

        //쇼핑 테이블
        String shopSQL = "create table shopWomen(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "tag text," +
                "photo text);";
        db.execSQL(shopSQL);
        db.execSQL("insert into shopWomen(name, tag, photo)values ('지그재그','#구글 앱  #통합 쇼핑앱', 'img_shop_zigzag')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('마이더스비','#1+1  #베이직 의류', 'img_shop_midasb')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('러브패리스','#고급적 #중저가  #오피스룩', 'img_shop_loveparis')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('저스트원','#청바지  #배기진', 'img_shop_justone')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('모코블링','#독특한 스타일  #호불호', 'img_shop_mocobling')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('피핀','#데일리룩 #키에 따른 바지 사이즈', 'img_shop_pippin')");

        String shopSQL2 = "create table shopMen(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "tag text," +
                "photo text);";
        db.execSQL(shopSQL2);
        db.execSQL("insert into shopMen(name, tag, photo)values ('무신사','#1+1  #베이직 의류', 'img_shop_midasb')");
        db.execSQL("insert into shopMen(name, tag, photo)values ('바이더알','#고급적 #중저가  #오피스룩', 'img_shop_loveparis')");
        db.execSQL("insert into shopMen(name, tag, photo)values ('멋남','#청바지  #배기진', 'img_shop_justone')");
        db.execSQL("insert into shopMen(name, tag, photo)values ('슈퍼스타아이','#독특한 스타일  #호불호', 'img_shop_mocobling')");
        db.execSQL("insert into shopMen(name, tag, photo)values ('지니푸','#데일리룩 #키에 따른 바지 사이즈', 'img_shop_pippin')");

        //일상 테이블
        String lifeSQL = "create table life(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "photo text);";
        db.execSQL(lifeSQL);
        db.execSQL("insert into life(name, photo)values ('네이버 회원가입하기', 'img_logo_naver')");
        db.execSQL("insert into life(name, photo)values ('카카오톡 회원가입하기', 'img_logo_kakao')");
        db.execSQL("insert into life(name, photo)values ('지하철 1회용 카드 구매', 'img_info_subway')");
        db.execSQL("insert into life(name, photo)values ('배달의 민족 주문하기', 'img_logo_bedal')");

        //여행 테이블
        String travelSQL = "create table travel(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "photo text);";
        db.execSQL(travelSQL);
        db.execSQL("insert into travel(name, photo)values ('공주 하대리 칠석제', 'culture1')");
        db.execSQL("insert into travel(name, photo)values ('세계 다문화 박물관', 'culture2')");
        db.execSQL("insert into travel(name, photo)values ('한국 민속촌', 'culture3')");
        db.execSQL("insert into travel(name, photo)values ('안산 다문화거리', 'culture4')");
        db.execSQL("insert into travel(name, photo)values ('행복전통마을 구름에', 'culture5')");
        db.execSQL("insert into travel(name, photo)values ('마장축산물시장', 'culture6')");


        // 문제해결 테이블
        String problemSQL = "create table problem(" +
                "_id integer primary key autoincrement," +
                "title text," +
                "subtitle text," +
                "photo text);";
        db.execSQL(problemSQL);
        db.execSQL("insert into problem(title,subtitle,photo)values ('의료','Medical','img_medic')");
        db.execSQL("insert into problem(title,subtitle,photo)values ('법률','Law','img_law')");
        db.execSQL("insert into problem(title,subtitle,photo)values ('복지','Welefare','img_welfare')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == VERSION) {
            db.execSQL("drop table newWord");
            db.execSQL("drop table shopWomen");
            db.execSQL("drop table shopMen");
            db.execSQL("drop table life");
            db.execSQL("drop table problem");
            db.execSQL("drop table travel");
            onCreate(db);
        }
    }
}
