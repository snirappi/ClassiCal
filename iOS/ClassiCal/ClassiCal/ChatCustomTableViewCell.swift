//
//  ChatCustomTableViewCell.swift
//  ClassiCal
//
//  Created by ZengJintao on 3/31/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class ChatCustomTableViewCell: UITableViewCell {
    
    
    @IBOutlet weak var chatContent: UILabel!
    @IBOutlet weak var chatName: UILabel!
    @IBOutlet weak var chatReport: UILabel!
    
    @IBOutlet weak var myName: UILabel!
    
    
    @IBOutlet weak var myReply: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
}