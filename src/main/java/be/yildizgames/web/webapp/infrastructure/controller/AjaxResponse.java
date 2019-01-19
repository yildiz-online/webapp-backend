/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildizgames.web.webapp.infrastructure.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class AjaxResponse {

    private final List<Redirection> redirections = new ArrayList<>();

    private final List<Notification> notifications;

    private AjaxResponse(List<Notification> notifications) {
        super();
        this.notifications = new ArrayList<>(notifications);
    }

    private AjaxResponse(Notification notification) {
        super();
        this.notifications = Collections.singletonList(notification);
    }

    public static AjaxResponse notification(Notification notification) {
        return new AjaxResponse(notification);
    }

    public static AjaxResponse notification(List<Notification> notifications) {
        return new AjaxResponse(notifications);
    }

    public List<Redirection> getRedirections() {
        return redirections;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
