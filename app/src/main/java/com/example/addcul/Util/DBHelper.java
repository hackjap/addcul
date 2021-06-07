package com.example.addcul.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSION = 105;

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

        db.execSQL("insert into newWord(num, name, info, subInfo)values ('1','군싹', '[ 군침이 싹 돈다 ]', '음식이 맛있어 보여 군침이 돈다는 것을 줄인 말이에요! 맛있는 음식을 봤을 때 사용해보아요!')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('2','스불재', '[ 스스로 불러온 재앙 ]', '자기 자신이 나쁜 결과를 만들었다는 뜻이에요! 실수했을 때 센스있게 사용해봐요!')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('3','주불', '[ 주소 불러 ]', '친구에게 주소를 물어볼 때 사용하는 말이에요! 센스있게 주소를 물어봐요!')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('4','재질', '[ 느낌, 스타일, 부류 ]', '요즘에는 옷 스타일 좋다~ 보다는 재질 좋다~라고 말해요! 재질로 친구 스타일 칭찬해봐요!')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('5','머선129', '[ 무슨 일이야? ]', '1(일) 2(이) 9(구)로 한국어 발음대로 무슨 일 있냐고 물어보는 뜻이에요! 친구에게 무슨 일이 있는지 궁금할 때 물어봐요! ')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('6','억텐', '[ 억지 텐션 ]', '억지 텐션의 줄임말로 억지로 즐거운 척한다는 뜻이에요! 억지로 무언가를 하려는 친구에게 장난으로 써봐요!')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('7','임포', '[ 스파이 | 배신자 ]', '임포스터라는 게임의 주인공으로 팀원을 배신하는 스파이 같은 뜻이에요! 배신하는 친구에게 임포라고 놀려봐요!')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('8','쫌쫌따리', '[ 조금씩 틈틈히 모으는 모양 ]', '조금씩 틈틈히 동전모으듯이 꾸준히 모은다는 뜻이에요! 꾸준히 모으는 뭔가가 있을 때 친구에게 좀좀따리라고 설명해봐요!')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('9','갑통알', '[ 갑자기 통장을 보니 알바해야겠다 ]', '통장에 돈이 별로 없어서 일해야한다는 뜻이에요! 이 말을 쓸 때는 많이 슬프겠죠....?')");
        db.execSQL("insert into newWord(num, name, info, subInfo)values ('10','애빼시', '[ 애교 빼면 시체 ]', '애교가 너무 많은 친구나 자신에게 쓰는 말이에요! 자신이 애교가 많다면 애빼시라고 소개해도 좋아요!')");


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //쇼핑 테이블
        String shopSQL = "create table shopWomen(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "tag text," +
                "photo text);";
        db.execSQL(shopSQL);
        db.execSQL("insert into shopWomen(name, tag, photo)values ('지그재그','#구글 앱\n#통합 쇼핑앱', 'img_shop_zigzag')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('마이더스비','#1+1\n#베이직 의류', 'img_shop_midasb')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('러브패리스','#고급적의류가 중저가\n#오피스룩', 'img_shop_loveparis')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('저스트원','#청바지\n#배기진', 'img_shop_justone')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('모코블링','#독특한 스타일\n#호불호', 'img_shop_mocobling')");
        db.execSQL("insert into shopWomen(name, tag, photo)values ('피핀','#데일리룩\n#키에 따른 바지 사이즈', 'img_shop_pippin')");

        String shopSQL2 = "create table shopMen(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "tag text," +
                "photo text);";
        db.execSQL(shopSQL2);
        db.execSQL("insert into shopMen(name, tag, photo)values ('무신사','#종합 쇼핑몰\n#무료 배송', 'img_shop_musinsa')");
        db.execSQL("insert into shopMen(name, tag, photo)values ('조군샵','#데일리룩 추천\n#셔츠 맛집', 'img_shop_jogunshop')");
        db.execSQL("insert into shopMen(name, tag, photo)values ('바이더알','#자체제작\n#셀렉트샵', 'img_shop_byther')");
        db.execSQL("insert into shopMen(name, tag, photo)values ('모던이프','#아우터\n#1+1', 'img_shop_modernif')");
        db.execSQL("insert into shopMen(name, tag, photo)values ('슈퍼스타아이','#1+1+1\n#코디 세트', 'img_shop_superstari')");
        db.execSQL("insert into shopMen(name, tag, photo)values ('락커룸','#신상 맛집\n#저렴한 가격', 'img_shop_locker')");

        String shopSQL3 = "create table shopApp(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "tag text," +
                "photo text);";
        db.execSQL(shopSQL3);
        db.execSQL("insert into shopApp(name, tag, photo)values ('다나와','#가격, 성능비교\n#최저가판매', 'img_shop_danawa')");
        db.execSQL("insert into shopApp(name, tag, photo)values ('삼성전자','#믿고 쓰는 삼성\n#케어플러스', 'img_shop_samsung')");
        db.execSQL("insert into shopApp(name, tag, photo)values ('LG전자','#케어솔루션\n#LG 가전제품은 최고', 'img_shop_lg')");
        db.execSQL("insert into shopApp(name, tag, photo)values ('하이마트','#2시간 퀵배송\n#종합 쇼핑몰', 'img_shop_himart')");
        db.execSQL("insert into shopApp(name, tag, photo)values ('전자랜드','#매일 새로운 이벤트\n#생활일착 컨텐츠', 'img_shop_junjaland')");

        String shopSQL4 = "create table shopMake(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "tag text," +
                "photo text);";
        db.execSQL(shopSQL4);
        db.execSQL("insert into shopMake(name, tag, photo)values ('미샤','#전품목 1+1\n#조기품절 주의', 'img_shop_misha')");
        db.execSQL("insert into shopMake(name, tag, photo)values ('토니모리','#푸짐한 사은품\n#수분크림으로 유명', 'img_shop_tonymori')");
        db.execSQL("insert into shopMake(name, tag, photo)values ('데페이스샵','#선크림 유명\n#선크림 전품목 1+1 ', 'img_shop_thefaceshop')");
        db.execSQL("insert into shopMake(name, tag, photo)values ('이니스프리','#필 마스크 맛집\n#여름쿠션 맛집', 'img_shop_innisfree')");
        db.execSQL("insert into shopMake(name, tag, photo)values ('에뛰드','#매일 신상품 출시\n#셀렉샵', 'img_shop_etude')");

        String shopSQL5 = "create table shopFood(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "tag text," +
                "photo text);";
        db.execSQL(shopSQL5);
        db.execSQL("insert into shopFood(name, tag, photo)values ('노브랜드','#초저가\n#가격대비 최상 품질', 'img_shop_nobrand')");
        db.execSQL("insert into shopFood(name, tag, photo)values ('홈플러스24','#품목별 깔끔한 안내\n#빠른 배송', 'img_shop_homeplus')");
        db.execSQL("insert into shopFood(name, tag, photo)values ('쿠팡','#로켓 배송\n#떠오르는 기업', 'img_shop_coupang')");
        db.execSQL("insert into shopFood(name, tag, photo)values ('마켓컬리','#첫 구매시 무료배송\n#신규회원 인기상품 100원', 'img_shop_marketkurly')");
        db.execSQL("insert into shopFood(name, tag, photo)values ('푸짐','#깔끔한 페이지 디자인\n#도심 속 직배송', 'img_shop_foodzim')");

        String shopSQL6 = "create table shopFurni(" +
                "_id integer primary key autoincrement," +
                "name text," +
                "tag text," +
                "photo text);";
        db.execSQL(shopSQL6);
        db.execSQL("insert into shopFurni(name, tag, photo)values ('이케아','#더 낮은 새로운 가격\n#가구 쇼핑몰의 대명사', 'img_shop_ikea')");
        db.execSQL("insert into shopFurni(name, tag, photo)values ('오늘의집','#베스트 상품 최대 80% 할인\n#방꾸미기', 'img_shop_todayhouse')");
        db.execSQL("insert into shopFurni(name, tag, photo)values ('다이소','#싼 가격\n#좋은 품질', 'img_shop_daiso')");
        db.execSQL("insert into shopFurni(name, tag, photo)values ('모던하우스','#신규가입 10%할인\n#셀럽이 제안하는 핫 가구', 'img_shop_modernhouse')");
        db.execSQL("insert into shopFurni(name, tag, photo)values ('까사미아','#모던한 디자인\n#하이퀄리티', 'img_shop_casamia')");


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




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
        db.execSQL("insert into problem(title,subtitle,photo)values ('취업','employ','img_employ')");
        db.execSQL("insert into problem(title,subtitle,photo)values ('복지','Welefare','img_welfare')");
        db.execSQL("insert into problem(title,subtitle,photo)values ('법률','Law','img_law')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == VERSION) {
            db.execSQL("drop table newWord");
            db.execSQL("drop table shopWomen");
            db.execSQL("drop table shopMen");
            db.execSQL("drop table shopApp");
            db.execSQL("drop table shopMake");
            db.execSQL("drop table shopFood");
            db.execSQL("drop table shopFurni");
            db.execSQL("drop table life");
            db.execSQL("drop table problem");
            db.execSQL("drop table travel");
            onCreate(db);
        }
    }
}
