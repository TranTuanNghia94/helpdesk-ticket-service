package com.it.ticket.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ticket_attachments")
public class TicketAttachmentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ticket_id", nullable = false)
    private UUID ticketId;
    
    @Column(name = "comment_id")
    private UUID commentId;

    @Column(name = "filename", nullable = false, length = 255)
    private String filename;

    @Column(name = "original_filename", nullable = false, length = 255)
    private String originalFilename;

    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    @Column(name = "checksum", length = 255)
    private String checksum;

    @Column(name = "is_image", nullable = false)
    private Boolean isImage;

    @Column(name = "thumbnail_path", length = 500)
    private String thumbnailPath;

    @Column(name = "uploaded_by_id", nullable = false)
    private UUID uploadedBy;
    
    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;
}
