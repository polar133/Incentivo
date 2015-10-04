package com.monkeycoders.incentavo.incentivo.models;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

@Table("notes")
class WishlistItem extends Model {

    @Column("title")
    public String title;

    @Column("body")
    public String body;



}