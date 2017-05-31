package com.zhuyong.supergank.model;

import java.util.List;

/**
 * Created by zhuyong on 2017/5/17.
 */

public class Weather {
    private String error;
    private String status;
    private String date;
    private List<DataBean> results;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DataBean> getResults() {
        return results;
    }

    public void setResults(List<DataBean> results) {
        this.results = results;
    }

    public static class DataBean {
        private String currentCity;
        private String pm25;
        private List<ResultIndexList> index;
        private List<ResultWeatherDataList> weather_data;

        public String getCurrentCity() {
            return currentCity;
        }

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public List<ResultIndexList> getIndex() {
            return index;
        }

        public void setIndex(List<ResultIndexList> index) {
            this.index = index;
        }

        public List<ResultWeatherDataList> getWeather_data() {
            return weather_data;
        }

        public void setWeather_data(List<ResultWeatherDataList> weather_data) {
            this.weather_data = weather_data;
        }

        public static class ResultIndexList {
            private String title;
            private String zs;
            private String tipt;
            private String des;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getZs() {
                return zs;
            }

            public void setZs(String zs) {
                this.zs = zs;
            }

            public String getTipt() {
                return tipt;
            }

            public void setTipt(String tipt) {
                this.tipt = tipt;
            }

            public String getDes() {
                return des;
            }

            public void setDes(String des) {
                this.des = des;
            }
        }

        public static class ResultWeatherDataList {
            private String date;
            private String dayPictureUrl;
            private String nightPictureUrl;
            private String weather;
            private String wind;
            private String temperature;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDayPictureUrl() {
                return dayPictureUrl;
            }

            public void setDayPictureUrl(String dayPictureUrl) {
                this.dayPictureUrl = dayPictureUrl;
            }

            public String getNightPictureUrl() {
                return nightPictureUrl;
            }

            public void setNightPictureUrl(String nightPictureUrl) {
                this.nightPictureUrl = nightPictureUrl;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }

    }

}

//{
//        "error": 0,
//        "status": "success",
//        "date": "2017-05-17",
//        "results": [
//        {
//        "currentCity": "绥德",
//        "pm25": "",
//        "index": [
//        {
//        "title": "穿衣",
//        "zs": "炎热",
//        "tipt": "穿衣指数",
//        "des": "天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"
//        },
//        {
//        "title": "洗车",
//        "zs": "较适宜",
//        "tipt": "洗车指数",
//        "des": "较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"
//        },
//        {
//        "title": "感冒",
//        "zs": "少发",
//        "tipt": "感冒指数",
//        "des": "各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"
//        },
//        {
//        "title": "运动",
//        "zs": "较不宜",
//        "tipt": "运动指数",
//        "des": "天气较好，无雨水困扰，但考虑气温很高，请注意适当减少运动时间并降低运动强度，运动后及时补充水分。"
//        },
//        {
//        "title": "紫外线强度",
//        "zs": "很强",
//        "tipt": "紫外线强度指数",
//        "des": "紫外线辐射极强，建议涂擦SPF20以上、PA++的防晒护肤品，尽量避免暴露于日光下。"
//        }
//        ],
//        "weather_data": [
//        {
//        "date": "周三 05月17日 (实时：29℃)",
//        "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/qing.png",
//        "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/qing.png",
//        "weather": "晴",
//        "wind": "微风",
//        "temperature": "32 ~ 13℃"
//        },
//        {
//        "date": "周四",
//        "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/yin.png",
//        "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/qing.png",
//        "weather": "阴转晴",
//        "wind": "微风",
//        "temperature": "30 ~ 14℃"
//        },
//        {
//        "date": "周五",
//        "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/xiaoyu.png",
//        "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/xiaoyu.png",
//        "weather": "小雨",
//        "wind": "微风",
//        "temperature": "29 ~ 15℃"
//        },
//        {
//        "date": "周六",
//        "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/qing.png",
//        "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/yin.png",
//        "weather": "晴转阴",
//        "wind": "微风",
//        "temperature": "27 ~ 16℃"
//        }
//        ]
//        }
//        ]
//        }

