package com.music.qichaoqun.music.bean;

import java.util.List;

public class SingleMusic {

    /**
     * songinfo : {"resource_type_ext":"0","si_presale_flag":"0","resource_type":"0","mv_provider":"0100000000","del_status":"1","collect_num":819,"hot":"292","res_reward_flag":"0","sound_effect":"","title":"海阔天空","language":"粤语","play_type":0,"country":"港台","biaoshi":"lossless,perm-2","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","artist_1000_1000":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_1000,h_1000","is_first_publish":0,"artist_640_1136":"","charge":0,"copy_type":"0","share_url":"http://music.baidu.com/song/877578","has_mv_mobile":1,"album_no":"1","is_charge":"","special_type":0,"has_filmtv":"0","pic_huge":"","ting_uid":"1100","expire":36000,"havehigh":2,"si_proxycompany":"正东音乐娱乐咨询（北京）有限公司","compose":"","toneid":"600902000004240302","area":"1","info":"","artist_500_500":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_500,h_500","multiterminal_copytype":"","has_mv":1,"total_listen_nums":"43924","song_id":"877578","piao_id":"0","high_rate":"320","artist":"Beyond","artist_list":[{"avatar_mini":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_20,h_20","avatar_s300":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_300,h_300","ting_uid":"1100","del_status":"0","avatar_s500":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_500,h_500","artist_name":"Beyond","avatar_small":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_48,h_48","avatar_s180":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_180,h_180","artist_id":"130"}],"comment_num":148,"compress_status":"0","original":0,"artist_480_800":"","relate_status":"1","learn":1,"bitrate":"96,224,128,320","pic_big":"http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_150,h_150","album_1000_1000":"","pic_singer":"","songwriting":"","album_500_500":"http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_500,h_500","song_source":"web","album_id":"197864","share_num":197,"aliasname":"","artist_id":"130","korean_bb_song":"0","all_rate":"96,128,224,320,flac","author":"Beyond","all_artist_id":"130","listen_nums":"37","publishtime":"1993-09-01","versions":"","all_artist_ting_uid":"1100","res_encryption_flag":"0","file_duration":"322","pic_radio":"http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_300,h_300","distribution":"1100000000,1100000000,1100000000,1100000000,1100000000,1100000000,1100000000,1111111111,1111111111,1111111111","lrclink":"http://qukufile2.qianqian.com/data2/lrc/238975978/238975978.lrc","pic_small":"http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_90,h_90","album_title":"海阔天空","original_rate":"","pic_premium":"http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_500,h_500"}
     * error_code : 22000
     * bitrate : {"show_link":"http://zhangmenshiting.qianqian.com/data2/music/c0c05e4a304bfc69a89e3730e3413fde/599516589/599516589.m4a?xcode=6e91a2a9a3daf52df1962d1d1690fc93","free":1,"replay_gain":"0.000000","song_file_id":599516589,"file_size":3940197,"file_extension":"m4a","file_duration":322,"file_format":"aac","file_bitrate":96,"file_link":"http://zhangmenshiting.qianqian.com/data2/music/c0c05e4a304bfc69a89e3730e3413fde/599516589/599516589.m4a?xcode=6e91a2a9a3daf52df1962d1d1690fc93","original":0}
     */

    private SonginfoBean songinfo;
    private int error_code;
    private BitrateBean bitrate;

    public SonginfoBean getSonginfo() {
        return songinfo;
    }

    public void setSonginfo(SonginfoBean songinfo) {
        this.songinfo = songinfo;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public BitrateBean getBitrate() {
        return bitrate;
    }

    public void setBitrate(BitrateBean bitrate) {
        this.bitrate = bitrate;
    }

    public static class SonginfoBean {
        /**
         * resource_type_ext : 0
         * si_presale_flag : 0
         * resource_type : 0
         * mv_provider : 0100000000
         * del_status : 1
         * collect_num : 819
         * hot : 292
         * res_reward_flag : 0
         * sound_effect :
         * title : 海阔天空
         * language : 粤语
         * play_type : 0
         * country : 港台
         * biaoshi : lossless,perm-2
         * bitrate_fee : {"0":"0|0","1":"0|0"}
         * artist_1000_1000 : http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_1000,h_1000
         * is_first_publish : 0
         * artist_640_1136 :
         * charge : 0
         * copy_type : 0
         * share_url : http://music.baidu.com/song/877578
         * has_mv_mobile : 1
         * album_no : 1
         * is_charge :
         * special_type : 0
         * has_filmtv : 0
         * pic_huge :
         * ting_uid : 1100
         * expire : 36000
         * havehigh : 2
         * si_proxycompany : 正东音乐娱乐咨询（北京）有限公司
         * compose :
         * toneid : 600902000004240302
         * area : 1
         * info :
         * artist_500_500 : http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_500,h_500
         * multiterminal_copytype :
         * has_mv : 1
         * total_listen_nums : 43924
         * song_id : 877578
         * piao_id : 0
         * high_rate : 320
         * artist : Beyond
         * artist_list : [{"avatar_mini":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_20,h_20","avatar_s300":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_300,h_300","ting_uid":"1100","del_status":"0","avatar_s500":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_500,h_500","artist_name":"Beyond","avatar_small":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_48,h_48","avatar_s180":"http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_180,h_180","artist_id":"130"}]
         * comment_num : 148
         * compress_status : 0
         * original : 0
         * artist_480_800 :
         * relate_status : 1
         * learn : 1
         * bitrate : 96,224,128,320
         * pic_big : http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_150,h_150
         * album_1000_1000 :
         * pic_singer :
         * songwriting :
         * album_500_500 : http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_500,h_500
         * song_source : web
         * album_id : 197864
         * share_num : 197
         * aliasname :
         * artist_id : 130
         * korean_bb_song : 0
         * all_rate : 96,128,224,320,flac
         * author : Beyond
         * all_artist_id : 130
         * listen_nums : 37
         * publishtime : 1993-09-01
         * versions :
         * all_artist_ting_uid : 1100
         * res_encryption_flag : 0
         * file_duration : 322
         * pic_radio : http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_300,h_300
         * distribution : 1100000000,1100000000,1100000000,1100000000,1100000000,1100000000,1100000000,1111111111,1111111111,1111111111
         * lrclink : http://qukufile2.qianqian.com/data2/lrc/238975978/238975978.lrc
         * pic_small : http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_90,h_90
         * album_title : 海阔天空
         * original_rate :
         * pic_premium : http://qukufile2.qianqian.com/data2/pic/88582702/88582702.jpg@s_2,w_500,h_500
         */

        private String resource_type_ext;
        private String si_presale_flag;
        private String resource_type;
        private String mv_provider;
        private String del_status;
        private int collect_num;
        private String hot;
        private String res_reward_flag;
        private String sound_effect;
        private String title;
        private String language;
        private int play_type;
        private String country;
        private String biaoshi;
        private String bitrate_fee;
        private String artist_1000_1000;
        private int is_first_publish;
        private String artist_640_1136;
        private int charge;
        private String copy_type;
        private String share_url;
        private int has_mv_mobile;
        private String album_no;
        private String is_charge;
        private int special_type;
        private String has_filmtv;
        private String pic_huge;
        private String ting_uid;
        private int expire;
        private int havehigh;
        private String si_proxycompany;
        private String compose;
        private String toneid;
        private String area;
        private String info;
        private String artist_500_500;
        private String multiterminal_copytype;
        private int has_mv;
        private String total_listen_nums;
        private String song_id;
        private String piao_id;
        private String high_rate;
        private String artist;
        private int comment_num;
        private String compress_status;
        private int original;
        private String artist_480_800;
        private String relate_status;
        private int learn;
        private String bitrate;
        private String pic_big;
        private String album_1000_1000;
        private String pic_singer;
        private String songwriting;
        private String album_500_500;
        private String song_source;
        private String album_id;
        private int share_num;
        private String aliasname;
        private String artist_id;
        private String korean_bb_song;
        private String all_rate;
        private String author;
        private String all_artist_id;
        private String listen_nums;
        private String publishtime;
        private String versions;
        private String all_artist_ting_uid;
        private String res_encryption_flag;
        private String file_duration;
        private String pic_radio;
        private String distribution;
        private String lrclink;
        private String pic_small;
        private String album_title;
        private String original_rate;
        private String pic_premium;
        private List<ArtistListBean> artist_list;

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getSi_presale_flag() {
            return si_presale_flag;
        }

        public void setSi_presale_flag(String si_presale_flag) {
            this.si_presale_flag = si_presale_flag;
        }

        public String getResource_type() {
            return resource_type;
        }

        public void setResource_type(String resource_type) {
            this.resource_type = resource_type;
        }

        public String getMv_provider() {
            return mv_provider;
        }

        public void setMv_provider(String mv_provider) {
            this.mv_provider = mv_provider;
        }

        public String getDel_status() {
            return del_status;
        }

        public void setDel_status(String del_status) {
            this.del_status = del_status;
        }

        public int getCollect_num() {
            return collect_num;
        }

        public void setCollect_num(int collect_num) {
            this.collect_num = collect_num;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getRes_reward_flag() {
            return res_reward_flag;
        }

        public void setRes_reward_flag(String res_reward_flag) {
            this.res_reward_flag = res_reward_flag;
        }

        public String getSound_effect() {
            return sound_effect;
        }

        public void setSound_effect(String sound_effect) {
            this.sound_effect = sound_effect;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public int getPlay_type() {
            return play_type;
        }

        public void setPlay_type(int play_type) {
            this.play_type = play_type;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getBiaoshi() {
            return biaoshi;
        }

        public void setBiaoshi(String biaoshi) {
            this.biaoshi = biaoshi;
        }

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public String getArtist_1000_1000() {
            return artist_1000_1000;
        }

        public void setArtist_1000_1000(String artist_1000_1000) {
            this.artist_1000_1000 = artist_1000_1000;
        }

        public int getIs_first_publish() {
            return is_first_publish;
        }

        public void setIs_first_publish(int is_first_publish) {
            this.is_first_publish = is_first_publish;
        }

        public String getArtist_640_1136() {
            return artist_640_1136;
        }

        public void setArtist_640_1136(String artist_640_1136) {
            this.artist_640_1136 = artist_640_1136;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public String getCopy_type() {
            return copy_type;
        }

        public void setCopy_type(String copy_type) {
            this.copy_type = copy_type;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public int getHas_mv_mobile() {
            return has_mv_mobile;
        }

        public void setHas_mv_mobile(int has_mv_mobile) {
            this.has_mv_mobile = has_mv_mobile;
        }

        public String getAlbum_no() {
            return album_no;
        }

        public void setAlbum_no(String album_no) {
            this.album_no = album_no;
        }

        public String getIs_charge() {
            return is_charge;
        }

        public void setIs_charge(String is_charge) {
            this.is_charge = is_charge;
        }

        public int getSpecial_type() {
            return special_type;
        }

        public void setSpecial_type(int special_type) {
            this.special_type = special_type;
        }

        public String getHas_filmtv() {
            return has_filmtv;
        }

        public void setHas_filmtv(String has_filmtv) {
            this.has_filmtv = has_filmtv;
        }

        public String getPic_huge() {
            return pic_huge;
        }

        public void setPic_huge(String pic_huge) {
            this.pic_huge = pic_huge;
        }

        public String getTing_uid() {
            return ting_uid;
        }

        public void setTing_uid(String ting_uid) {
            this.ting_uid = ting_uid;
        }

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public int getHavehigh() {
            return havehigh;
        }

        public void setHavehigh(int havehigh) {
            this.havehigh = havehigh;
        }

        public String getSi_proxycompany() {
            return si_proxycompany;
        }

        public void setSi_proxycompany(String si_proxycompany) {
            this.si_proxycompany = si_proxycompany;
        }

        public String getCompose() {
            return compose;
        }

        public void setCompose(String compose) {
            this.compose = compose;
        }

        public String getToneid() {
            return toneid;
        }

        public void setToneid(String toneid) {
            this.toneid = toneid;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getArtist_500_500() {
            return artist_500_500;
        }

        public void setArtist_500_500(String artist_500_500) {
            this.artist_500_500 = artist_500_500;
        }

        public String getMultiterminal_copytype() {
            return multiterminal_copytype;
        }

        public void setMultiterminal_copytype(String multiterminal_copytype) {
            this.multiterminal_copytype = multiterminal_copytype;
        }

        public int getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(int has_mv) {
            this.has_mv = has_mv;
        }

        public String getTotal_listen_nums() {
            return total_listen_nums;
        }

        public void setTotal_listen_nums(String total_listen_nums) {
            this.total_listen_nums = total_listen_nums;
        }

        public String getSong_id() {
            return song_id;
        }

        public void setSong_id(String song_id) {
            this.song_id = song_id;
        }

        public String getPiao_id() {
            return piao_id;
        }

        public void setPiao_id(String piao_id) {
            this.piao_id = piao_id;
        }

        public String getHigh_rate() {
            return high_rate;
        }

        public void setHigh_rate(String high_rate) {
            this.high_rate = high_rate;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public String getCompress_status() {
            return compress_status;
        }

        public void setCompress_status(String compress_status) {
            this.compress_status = compress_status;
        }

        public int getOriginal() {
            return original;
        }

        public void setOriginal(int original) {
            this.original = original;
        }

        public String getArtist_480_800() {
            return artist_480_800;
        }

        public void setArtist_480_800(String artist_480_800) {
            this.artist_480_800 = artist_480_800;
        }

        public String getRelate_status() {
            return relate_status;
        }

        public void setRelate_status(String relate_status) {
            this.relate_status = relate_status;
        }

        public int getLearn() {
            return learn;
        }

        public void setLearn(int learn) {
            this.learn = learn;
        }

        public String getBitrate() {
            return bitrate;
        }

        public void setBitrate(String bitrate) {
            this.bitrate = bitrate;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public String getAlbum_1000_1000() {
            return album_1000_1000;
        }

        public void setAlbum_1000_1000(String album_1000_1000) {
            this.album_1000_1000 = album_1000_1000;
        }

        public String getPic_singer() {
            return pic_singer;
        }

        public void setPic_singer(String pic_singer) {
            this.pic_singer = pic_singer;
        }

        public String getSongwriting() {
            return songwriting;
        }

        public void setSongwriting(String songwriting) {
            this.songwriting = songwriting;
        }

        public String getAlbum_500_500() {
            return album_500_500;
        }

        public void setAlbum_500_500(String album_500_500) {
            this.album_500_500 = album_500_500;
        }

        public String getSong_source() {
            return song_source;
        }

        public void setSong_source(String song_source) {
            this.song_source = song_source;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public int getShare_num() {
            return share_num;
        }

        public void setShare_num(int share_num) {
            this.share_num = share_num;
        }

        public String getAliasname() {
            return aliasname;
        }

        public void setAliasname(String aliasname) {
            this.aliasname = aliasname;
        }

        public String getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getKorean_bb_song() {
            return korean_bb_song;
        }

        public void setKorean_bb_song(String korean_bb_song) {
            this.korean_bb_song = korean_bb_song;
        }

        public String getAll_rate() {
            return all_rate;
        }

        public void setAll_rate(String all_rate) {
            this.all_rate = all_rate;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAll_artist_id() {
            return all_artist_id;
        }

        public void setAll_artist_id(String all_artist_id) {
            this.all_artist_id = all_artist_id;
        }

        public String getListen_nums() {
            return listen_nums;
        }

        public void setListen_nums(String listen_nums) {
            this.listen_nums = listen_nums;
        }

        public String getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(String publishtime) {
            this.publishtime = publishtime;
        }

        public String getVersions() {
            return versions;
        }

        public void setVersions(String versions) {
            this.versions = versions;
        }

        public String getAll_artist_ting_uid() {
            return all_artist_ting_uid;
        }

        public void setAll_artist_ting_uid(String all_artist_ting_uid) {
            this.all_artist_ting_uid = all_artist_ting_uid;
        }

        public String getRes_encryption_flag() {
            return res_encryption_flag;
        }

        public void setRes_encryption_flag(String res_encryption_flag) {
            this.res_encryption_flag = res_encryption_flag;
        }

        public String getFile_duration() {
            return file_duration;
        }

        public void setFile_duration(String file_duration) {
            this.file_duration = file_duration;
        }

        public String getPic_radio() {
            return pic_radio;
        }

        public void setPic_radio(String pic_radio) {
            this.pic_radio = pic_radio;
        }

        public String getDistribution() {
            return distribution;
        }

        public void setDistribution(String distribution) {
            this.distribution = distribution;
        }

        public String getLrclink() {
            return lrclink;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public String getOriginal_rate() {
            return original_rate;
        }

        public void setOriginal_rate(String original_rate) {
            this.original_rate = original_rate;
        }

        public String getPic_premium() {
            return pic_premium;
        }

        public void setPic_premium(String pic_premium) {
            this.pic_premium = pic_premium;
        }

        public List<ArtistListBean> getArtist_list() {
            return artist_list;
        }

        public void setArtist_list(List<ArtistListBean> artist_list) {
            this.artist_list = artist_list;
        }

        public static class ArtistListBean {
            /**
             * avatar_mini : http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_20,h_20
             * avatar_s300 : http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_300,h_300
             * ting_uid : 1100
             * del_status : 0
             * avatar_s500 : http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_500,h_500
             * artist_name : Beyond
             * avatar_small : http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_48,h_48
             * avatar_s180 : http://qukufile2.qianqian.com/data2/pic/8876f3d44df7664a1cd9cd1d15d88071/580370070/580370070.jpg@s_2,w_180,h_180
             * artist_id : 130
             */

            private String avatar_mini;
            private String avatar_s300;
            private String ting_uid;
            private String del_status;
            private String avatar_s500;
            private String artist_name;
            private String avatar_small;
            private String avatar_s180;
            private String artist_id;

            public String getAvatar_mini() {
                return avatar_mini;
            }

            public void setAvatar_mini(String avatar_mini) {
                this.avatar_mini = avatar_mini;
            }

            public String getAvatar_s300() {
                return avatar_s300;
            }

            public void setAvatar_s300(String avatar_s300) {
                this.avatar_s300 = avatar_s300;
            }

            public String getTing_uid() {
                return ting_uid;
            }

            public void setTing_uid(String ting_uid) {
                this.ting_uid = ting_uid;
            }

            public String getDel_status() {
                return del_status;
            }

            public void setDel_status(String del_status) {
                this.del_status = del_status;
            }

            public String getAvatar_s500() {
                return avatar_s500;
            }

            public void setAvatar_s500(String avatar_s500) {
                this.avatar_s500 = avatar_s500;
            }

            public String getArtist_name() {
                return artist_name;
            }

            public void setArtist_name(String artist_name) {
                this.artist_name = artist_name;
            }

            public String getAvatar_small() {
                return avatar_small;
            }

            public void setAvatar_small(String avatar_small) {
                this.avatar_small = avatar_small;
            }

            public String getAvatar_s180() {
                return avatar_s180;
            }

            public void setAvatar_s180(String avatar_s180) {
                this.avatar_s180 = avatar_s180;
            }

            public String getArtist_id() {
                return artist_id;
            }

            public void setArtist_id(String artist_id) {
                this.artist_id = artist_id;
            }
        }
    }

    public static class BitrateBean {
        /**
         * show_link : http://zhangmenshiting.qianqian.com/data2/music/c0c05e4a304bfc69a89e3730e3413fde/599516589/599516589.m4a?xcode=6e91a2a9a3daf52df1962d1d1690fc93
         * free : 1
         * replay_gain : 0.000000
         * song_file_id : 599516589
         * file_size : 3940197
         * file_extension : m4a
         * file_duration : 322
         * file_format : aac
         * file_bitrate : 96
         * file_link : http://zhangmenshiting.qianqian.com/data2/music/c0c05e4a304bfc69a89e3730e3413fde/599516589/599516589.m4a?xcode=6e91a2a9a3daf52df1962d1d1690fc93
         * original : 0
         */

        private String show_link;
        private int free;
        private String replay_gain;
        private int song_file_id;
        private int file_size;
        private String file_extension;
        private int file_duration;
        private String file_format;
        private int file_bitrate;
        private String file_link;
        private int original;

        public String getShow_link() {
            return show_link;
        }

        public void setShow_link(String show_link) {
            this.show_link = show_link;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public String getReplay_gain() {
            return replay_gain;
        }

        public void setReplay_gain(String replay_gain) {
            this.replay_gain = replay_gain;
        }

        public int getSong_file_id() {
            return song_file_id;
        }

        public void setSong_file_id(int song_file_id) {
            this.song_file_id = song_file_id;
        }

        public int getFile_size() {
            return file_size;
        }

        public void setFile_size(int file_size) {
            this.file_size = file_size;
        }

        public String getFile_extension() {
            return file_extension;
        }

        public void setFile_extension(String file_extension) {
            this.file_extension = file_extension;
        }

        public int getFile_duration() {
            return file_duration;
        }

        public void setFile_duration(int file_duration) {
            this.file_duration = file_duration;
        }

        public String getFile_format() {
            return file_format;
        }

        public void setFile_format(String file_format) {
            this.file_format = file_format;
        }

        public int getFile_bitrate() {
            return file_bitrate;
        }

        public void setFile_bitrate(int file_bitrate) {
            this.file_bitrate = file_bitrate;
        }

        public String getFile_link() {
            return file_link;
        }

        public void setFile_link(String file_link) {
            this.file_link = file_link;
        }

        public int getOriginal() {
            return original;
        }

        public void setOriginal(int original) {
            this.original = original;
        }
    }
}
