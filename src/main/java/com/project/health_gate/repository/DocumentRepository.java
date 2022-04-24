package com.project.health_gate.repository;



import com.project.health_gate.entities.Document;
import com.project.health_gate.entities.MedicalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;




@Repository
public interface DocumentRepository extends JpaRepository<Document,Long> {




    @Query("select new Document(d.FileId,d.name,d.size,d.uploadTime,d.content,d.discription) From Document d WHERE d.medicalfile=:medicalfile ORDER BY d.uploadTime DESC")
    List<Document> findDocumentsByMedicalfile(@Param("medicalfile") MedicalFile medicalfile);

    @Query("select new Document(d.FileId,d.name,d.size,d.uploadTime,d.content,d.medicalfile,d.discription) From Document d WHERE d.FileId=:FileId")
    Document findByFileId(@Param("FileId") Long FileId);

}
