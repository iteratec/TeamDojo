65,66c65,66
<     private static final String DEFAULT_LINK = "https://v.W.mvR";
<     private static final String UPDATED_LINK = "tw.hv.C-;";
---
>     private static final String DEFAULT_LINK = "AAAAAAAAAA";
>     private static final String UPDATED_LINK = "BBBBBBBBBB";
604,607c604,607
<             .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
<             .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
<             .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON.toString())))
<             .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK.toString())))
---
>             .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
>             .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
>             .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
>             .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
610c610
<             .andExpect(jsonPath("$.[*].suggestedBy").value(hasItem(DEFAULT_SUGGESTED_BY.toString())));
---
>             .andExpect(jsonPath("$.[*].suggestedBy").value(hasItem(DEFAULT_SUGGESTED_BY)));
