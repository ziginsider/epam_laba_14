# epam_laba_14https://github.com/ziginsider/epam_laba_14
Simple Image Loader for Android

## Demo work && screenshots

see [video](https://youtu.be/VV747h6uh6I)

<img alt="img1" src="img/img1.png">

## Принцип работы

Для тестирования изображения загружаются в RecyclerView. Сссылки (url) загружаемых изображений берутся с api.flickr.com (flickr.photos.getRecent) 

### ImageLoader

[ImageLoader.kt](https://github.com/ziginsider/epam_laba_14/blob/develop/app/src/main/java/io/github/ziginsider/epam_laba14/ImageLoader.kt)

[displayImage(view: View, url: String)](https://github.com/ziginsider/epam_laba_14/blob/develop/app/src/main/java/io/github/ziginsider/epam_laba14/ImageLoader.kt#L85) - метод, который получает контейнер для изображения и ссылку на изображение. Проверяет есть ли изображение в кэше. Если есть, выводит помещает его асинхронно в контейнер. Если нет, ставит в DownloadCompletionService задачу ImageDonloadTask на скачивание изображения.

[ImageDownloadTask](https://github.com/ziginsider/epam_laba_14/blob/develop/app/src/main/java/io/github/ziginsider/epam_laba14/ImageLoader.kt#L106) - реализация Callable<Image>, которая описывает задачу загрузки изображения. Возвращает результат: объект Image, который содержит контейнер для изображения, url и bitmap.

[DownloadCompletionService](https://github.com/ziginsider/epam_laba_14/blob/develop/app/src/main/java/io/github/ziginsider/epam_laba14/ImageLoader.kt#L147) - реализация ExecutorCompletionService<Image>, которая содержит среду выполнения Executor и предоставляет методы shutdown() и isTerminated() для управления этой средой
  
[ConsumerThread](https://github.com/ziginsider/epam_laba_14/blob/develop/app/src/main/java/io/github/ziginsider/epam_laba14/ImageLoader.kt#L162) - поток-потребитель результатов работы DownloadCompletionService. Асинхронно добавляет загруженное изображение в контейнер. В каждой итерации поток-потребитель в течении секунды ждет появления новых результатов, затем проверяет не завершилась ли среда выполнения.



