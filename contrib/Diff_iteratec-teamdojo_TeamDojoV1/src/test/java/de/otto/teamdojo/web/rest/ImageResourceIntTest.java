32a33
> 
48,50d48
<     private static final byte[] JPG_BLACK = new byte[] { -1, -40, -1, -32, 0, 16, 74, 70, 73, 70, 0, 1, 1, 1, 1, 44, 1, 44, 0, 0, -1, -37, 0, 67, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -37, 0, 67, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -64, 0, 17, 8, 0, 1, 0, 1, 3, 1, 17, 0, 2, 17, 1, 3, 17, 1, -1, -60, 0, 20, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -1, -60, 0, 20, 16, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -60, 0, 20, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -60, 0, 20, 17, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -38, 0, 12, 3, 1, 0, 2, 17, 3, 17, 0, 63, 0, 48, 127, -1, -39 };
<     private static final byte[] JPG_WHITE = new byte[] { -1, -40, -1, -32, 0, 16, 74, 70, 73, 70, 0, 1, 1, 1, 1, 44, 1, 44, 0, 0, -1, -37, 0, 67, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -37, 0, 67, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -64, 0, 17, 8, 0, 1, 0, 1, 3, 1, 17, 0, 2, 17, 1, 3, 17, 1, -1, -60, 0, 20, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, -1, -60, 0, 20, 16, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -60, 0, 20, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -60, 0, 20, 17, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -38, 0, 12, 3, 1, 0, 2, 17, 3, 17, 0, 63, 0, 64, 127, -1, -39 };
< 
54,55c52,53
<     private static final byte[] DEFAULT_SMALL = JPG_BLACK;
<     private static final byte[] UPDATED_SMALL = JPG_WHITE;
---
>     private static final byte[] DEFAULT_SMALL = TestUtil.createByteArray(1, "0");
>     private static final byte[] UPDATED_SMALL = TestUtil.createByteArray(1, "1");
57c55
<     private static final String UPDATED_SMALL_CONTENT_TYPE = "image/jpg";
---
>     private static final String UPDATED_SMALL_CONTENT_TYPE = "image/png";
59,60c57,58
<     private static final byte[] DEFAULT_MEDIUM = JPG_BLACK;
<     private static final byte[] UPDATED_MEDIUM = JPG_WHITE;
---
>     private static final byte[] DEFAULT_MEDIUM = TestUtil.createByteArray(1, "0");
>     private static final byte[] UPDATED_MEDIUM = TestUtil.createByteArray(1, "1");
62c60
<     private static final String UPDATED_MEDIUM_CONTENT_TYPE = "image/jpg";
---
>     private static final String UPDATED_MEDIUM_CONTENT_TYPE = "image/png";
64,65c62,63
<     private static final byte[] DEFAULT_LARGE = JPG_BLACK;
<     private static final byte[] UPDATED_LARGE = JPG_WHITE;
---
>     private static final byte[] DEFAULT_LARGE = TestUtil.createByteArray(1, "0");
>     private static final byte[] UPDATED_LARGE = TestUtil.createByteArray(1, "1");
67,71c65
<     private static final String UPDATED_LARGE_CONTENT_TYPE = "image/jpg";
< 
<     private static final byte[] EXPECTED_IMAGE_BLACK = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 1, 0, 0, 0, 1, 8, 2, 0, 0, 0, -112, 119, 83, -34, 0, 0, 0, 12, 73, 68, 65, 84, 120, -38, 99, 96, 100, 100, 4, 0, 0, 10, 0, 4, 89, -118, 90, -125, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126 };
<     private static final byte[] EXPECTED_IMAGE_WHITE = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 1, 0, 0, 0, 1, 8, 2, 0, 0, 0, -112, 119, 83, -34, 0, 0, 0, 12, 73, 68, 65, 84, 120, -38, 99, -8, -1, -1, 63, 0, 5, -2, 2, -2, 51, 18, -107, 20, 0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126 };
<     private static final String EXPECTED_CONTENT_TYPE = "image/png";
---
>     private static final String UPDATED_LARGE_CONTENT_TYPE = "image/png";
73,74c67,68
<     private static final String DEFAULT_HASH = "A2021C9ED42E09243D51F00D17862BE3";
<     private static final String UPDATED_HASH = "08332C69A77B3D73376DF2FC43D9B0C0";
---
>     private static final String DEFAULT_HASH = "AAAAAAAAAA";
>     private static final String UPDATED_HASH = "BBBBBBBBBB";
160,165c154,159
<         assertThat(testImage.getSmall()).containsExactly(EXPECTED_IMAGE_BLACK);
<         assertThat(testImage.getSmallContentType()).isEqualTo(EXPECTED_CONTENT_TYPE);
<         assertThat(testImage.getMedium()).containsExactly(EXPECTED_IMAGE_BLACK);
<         assertThat(testImage.getMediumContentType()).isEqualTo(EXPECTED_CONTENT_TYPE);
<         assertThat(testImage.getLarge()).containsExactly(EXPECTED_IMAGE_BLACK);
<         assertThat(testImage.getLargeContentType()).isEqualTo(EXPECTED_CONTENT_TYPE);
---
>         assertThat(testImage.getSmall()).isEqualTo(DEFAULT_SMALL);
>         assertThat(testImage.getSmallContentType()).isEqualTo(DEFAULT_SMALL_CONTENT_TYPE);
>         assertThat(testImage.getMedium()).isEqualTo(DEFAULT_MEDIUM);
>         assertThat(testImage.getMediumContentType()).isEqualTo(DEFAULT_MEDIUM_CONTENT_TYPE);
>         assertThat(testImage.getLarge()).isEqualTo(DEFAULT_LARGE);
>         assertThat(testImage.getLargeContentType()).isEqualTo(DEFAULT_LARGE_CONTENT_TYPE);
410,415c404,409
<         assertThat(testImage.getSmall()).isEqualTo(EXPECTED_IMAGE_WHITE);
<         assertThat(testImage.getSmallContentType()).isEqualTo(EXPECTED_CONTENT_TYPE);
<         assertThat(testImage.getMedium()).containsExactly(EXPECTED_IMAGE_WHITE);
<         assertThat(testImage.getMediumContentType()).isEqualTo(EXPECTED_CONTENT_TYPE);
<         assertThat(testImage.getLarge()).isEqualTo(EXPECTED_IMAGE_WHITE);
<         assertThat(testImage.getLargeContentType()).isEqualTo(EXPECTED_CONTENT_TYPE);
---
>         assertThat(testImage.getSmall()).isEqualTo(UPDATED_SMALL);
>         assertThat(testImage.getSmallContentType()).isEqualTo(UPDATED_SMALL_CONTENT_TYPE);
>         assertThat(testImage.getMedium()).isEqualTo(UPDATED_MEDIUM);
>         assertThat(testImage.getMediumContentType()).isEqualTo(UPDATED_MEDIUM_CONTENT_TYPE);
>         assertThat(testImage.getLarge()).isEqualTo(UPDATED_LARGE);
>         assertThat(testImage.getLargeContentType()).isEqualTo(UPDATED_LARGE_CONTENT_TYPE);
