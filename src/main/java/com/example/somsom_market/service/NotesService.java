package com.example.somsom_market.service;

import com.example.somsom_market.dao.NotesDao;
import com.example.somsom_market.domain.Notes;
import com.example.somsom_market.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService {
    @Autowired
    private NotesRepository notesRepository;
    public void setNotesRepository(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Autowired
    private NotesDao notesDao;
    public void setNotesDao(NotesDao notesDao) {
        this.notesDao = notesDao;
    }

    /* 쪽지 보내기 */
    public boolean insertNotes(String from_account_id, Long item_id, String to_seller_id,
                               String title, String content) {
        Notes notes = new Notes();
        notes.setFromAccountId(from_account_id);
        notes.setFromItemId(item_id);
        notes.setToSellerId(to_seller_id);
        notes.setTitle(title);
        notes.setContent(content);
        notes.setSendedAt(new Date());
        notes.setFromDel("N");
        notes.setToDel("N");


        Notes newNotes = notesDao.insertNotes(notes);
        if (newNotes.getNotesId() != null) {
            return true;
        }
        return false;
    }

    /* 쪽지 지우기 */
    
    /* 받은 쪽지 리스트 */
    public List<Notes> receivedNotes(String toSellerId) {
        return notesRepository.findByToSellerIdOrderBySendedAtDesc(toSellerId);
    }

    /* 보낸 쪽지 리스트 */
    public List<Notes> sendedNotes(String fromAccountId) {
        return notesRepository.findByFromAccountIdOrderBySendedAtDesc(fromAccountId);
    }

    /* 개별 쪽지 열람 */
    public Notes searchNotes(Long notesId) {
        Optional<Notes> notes = notesRepository.findById(notesId);
        Notes note = null;
        if (notes.isPresent()) {
            note = notes.get();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            note.setSendDate(formatter.format(note.getSendedAt()));
            if (note.getReadedAt() != null) {
                note.setReadDate(formatter.format(note.getReadedAt()));
            }
            return note;
        }
        return null;
    }

    /* 받은 쪽지 처음 열람 시, */
    public Notes updateReaded(Notes notes) {
        notes.setReadedAt(new Date());
        Notes newNotes = notesDao.updateReaded(notes);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        notes.setReadDate(formatter.format(newNotes.getReadedAt()));

        return newNotes;
    }
}
